package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_stock_item_list.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.*
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.StockItemListAdapter
import ps.leic.isel.pt.gis.utils.State
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

    private var state: State = State.LOADING
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ConstraintLayout

    private lateinit var housesSpinner: Spinner

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
            url = it.getString(URL_KEY)
        }
        stockItemListViewModel = ViewModelProviders.of(this).get(StockItemListViewModel::class.java)
        housesViewModel = ViewModelProviders.of(this).get(HousesViewModel::class.java)
        housesViewModel.init(url)
        getHouses()
    }

    private fun getHouses() {
        housesViewModel.getHouses()?.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> onSuccess(it.data)
                Status.UNSUCCESS -> onUnsuccess(it.apiError)
                Status.ERROR -> onError(it.message)
                Status.LOADING -> {
                    state = State.LOADING
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stock_item_list, container, false)
        view.stockItemListRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.stockItemListRecyclerView.setHasFixedSize(true)
        view.stockItemListRecyclerView.adapter = stockItemListAdapter
        stockItemListAdapter.setOnItemClickListener(this)

        housesSpinner = view.housesSpinner

        setSpinnerData()

        progressBar = view.stockItemListProgressBar
        content = view.stockItemListLayout

        // Show progress bar or content
        showProgressBarOrContent()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(URL_KEY)

        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.in_stock)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL_KEY, url)
    }

    override fun onStop() {
        super.onStop()
        housesViewModel.cancel()
        stockItemListViewModel.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Auxiliary Methods
     ***/

    private fun onSuccess(houses: HousesDto?) {
        houses?.let {
            //TODO: listas vazias
            // Show progress bar or content
            showProgressBarOrContent()

            this.houses = it.houses

            setSpinnerData()

            val size = this.houses?.size ?: 0
            if (size > 0) {
                this.houses?.get(0)?.links?.stockItemsLink?.let {
                    getHouseStockItemList(it)
                }
            }
        }
    }

    private fun getHouseStockItemList(url: String) {
        stockItemListViewModel.init(url)
        stockItemListViewModel.getStockItems()?.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> onSuccess(it.data)
                Status.UNSUCCESS -> onUnsuccess(it.apiError)
                Status.ERROR -> onError(it.message)
                Status.LOADING -> {
                    state = State.LOADING
                }
            }
        })
    }

    private fun onSuccess(stockItems: StockItemsDto?) {
        stockItems?.let {
            state = State.SUCCESS
            stockItemListAdapter.setData(it.stockItems)
            this.stockItems = it.stockItems
            showProgressBarOrContent()
        }
    }

    private fun onUnsuccess(error: ErrorDto?) {
        state = State.ERROR
        error?.let {
            Log.e(TAG, it.developerErrorMessage)
            onError(it.message)
        }
    }

    private fun onError(message: String?) {
        state = State.ERROR
        message?.let {
            Log.e(TAG, it)
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            fragmentManager?.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    private fun showProgressBarOrContent() {
        progressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
        content.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
    }

    private fun setSpinnerData() {
        houses?.let {
            val spinnerAdapter = ArrayAdapter<String>(housesSpinner.context, android.R.layout.simple_spinner_item, it.map { house -> house.name })
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            housesSpinner.adapter = spinnerAdapter
            housesSpinner.onItemSelectedListener = this
            housesSpinner.setSelection(0)
        }
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
     * StockItemListFragment Factory
     */
    companion object {
        const val TAG: String = "StockItemListFragment"
        private const val URL_KEY: String = "URL"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment StockItemListFragment.
         */
        @JvmStatic
        fun newInstance(url: String) =
                StockItemListFragment().apply {
                    arguments = Bundle().apply {
                        putString(URL_KEY, url)
                    }
                }
    }
}
