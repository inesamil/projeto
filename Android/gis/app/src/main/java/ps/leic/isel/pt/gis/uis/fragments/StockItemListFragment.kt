package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_stock_item_list.*
import kotlinx.android.synthetic.main.fragment_stock_item_list.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.model.dtos.StockItemDto
import ps.leic.isel.pt.gis.model.dtos.StockItemsDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.StockItemListAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.viewModel.HousesViewModel
import ps.leic.isel.pt.gis.viewModel.StockItemListViewModel

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StockItemListFragment.OnStockItemListFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StockItemListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StockItemListFragment : Fragment(), StockItemListAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private val stockItemListAdapter = StockItemListAdapter()
    private var houses: Array<HouseDto>? = null
    private var stockItems: Array<StockItemDto>? = null

    private var listener: OnStockItemListFragmentInteractionListener? = null
    private lateinit var stockItemListViewModel: StockItemListViewModel
    private lateinit var housesViewModel: HousesViewModel
    private lateinit var url: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnStockItemListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnStockItemListFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ExtraUtils.URL)
        }
        getHouses(url)
    }

    private fun getHouses(url: String) {
        housesViewModel = ViewModelProviders.of(this).get(HousesViewModel::class.java)
        housesViewModel.init(url)
        housesViewModel.getHouses()?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> onSuccess(it.data!!)
                it?.status == Status.ERROR -> onError(it.message)
                it?.status == Status.LOADING -> {
                    stockItemListProgressBar.visibility = View.VISIBLE
                    stockItemListLayout.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun onSuccess(housesDto: HousesDto) {
        // Hide progress bar
        stockItemListProgressBar.visibility = View.GONE
        stockItemListLayout.visibility = View.VISIBLE

        houses = housesDto.houses

        houses?.let {
            val spinnerAdapter = ArrayAdapter<String>(view?.context, android.R.layout.simple_spinner_item, it.map { house -> house.name })
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view?.housesSpinner?.adapter = spinnerAdapter
            view?.housesSpinner?.onItemSelectedListener = this
            view?.housesSpinner?.setSelection(0)
        }

        val size = houses?.size ?: 0
        if (size > 0) {
            houses?.get(0)?.links?.stockItemsLink?.let {
                getHouseStockItemList(it)
            }
        }
    }

    private fun getHouseStockItemList(url: String) {
        stockItemListViewModel = ViewModelProviders.of(this).get(StockItemListViewModel::class.java)
        stockItemListViewModel.init(url)
        stockItemListViewModel.getStockItems()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
    }

    private fun onSuccess(stockItems: StockItemsDto) {
        stockItemListAdapter.setData(stockItems.stockItems)
        this.stockItems = stockItems.stockItems
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stock_item_list, container, false)
        view.stockItemListRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.stockItemListRecyclerView.setHasFixedSize(true)
        view.stockItemListRecyclerView.adapter = stockItemListAdapter
        stockItemListAdapter.setOnItemClickListener(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(ExtraUtils.URL)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.in_stock)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ExtraUtils.URL, url)
    }

    override fun onStop() {
        super.onStop()
        stockItemListViewModel.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Listeners
     ***/

    override fun onItemClick(view: View, position: Int) {
        stockItems?.let {
            val stockItem: StockItemDto = it[position]
            stockItem.links.selfLink?.let {
                listener?.onStockItemInteraction(it, stockItem.productName, stockItem.variety)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Nothing to do here
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.let {
            if (it.housesSpinner.selectedItem != position) {
                houses?.let {
                    it[position].links.stockItemsLink?.let {
                        getHouseStockItemList(it)
                    }
                }
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnStockItemListFragmentInteractionListener {
        fun onStockItemInteraction(url: String, productName: String, stockItemVariety: String)
    }

    /**
     * StcokItemListFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param args Arguments
         * @return A new instance of fragment StockItemListFragment.
         */
        @JvmStatic
        fun newInstance(url: String) =
                StockItemListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExtraUtils.URL, url)
                    }
                }
    }
}
