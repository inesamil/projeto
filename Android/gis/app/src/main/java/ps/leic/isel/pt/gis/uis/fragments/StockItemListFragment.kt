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
import kotlinx.android.synthetic.main.fragment_stock_item_list.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.CharacteristicsDTO
import ps.leic.isel.pt.gis.model.HouseDTO
import ps.leic.isel.pt.gis.model.MemberDTO
import ps.leic.isel.pt.gis.model.StockItemDTO
import ps.leic.isel.pt.gis.model.dtos.StockItemsDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.StockItemListAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
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

    private lateinit var username: String
    private lateinit var houses: Array<HouseDTO>
    private lateinit var stockItems: Array<StockItemDTO>

    private val first = 0

    private lateinit var stockItemListAdapter: StockItemListAdapter

    private var listener: OnStockItemListFragmentInteractionListener? = null
    private var stockItemListViewModel: StockItemListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ExtraUtils.USER_USERNAME)
        }
        stockItemListViewModel = ViewModelProviders.of(this).get(StockItemListViewModel::class.java)
        val url = ""
        stockItemListViewModel?.init(url)
        stockItemListViewModel?.getStockItems()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
        //TODO: Get data
        houses = arrayOf(
                HouseDTO(1, "Smith", CharacteristicsDTO(0, 0, 2, 0),
                        arrayOf(MemberDTO(1, "alice", true),
                                MemberDTO(1, "bob", false))),
                HouseDTO(2, "Jones", CharacteristicsDTO(0, 1, 2, 0),
                        arrayOf(MemberDTO(2, "carol", true),
                                MemberDTO(2, "david", true))))
        // TODO: get stock item list for the first house
        stockItems = arrayOf(
                StockItemDTO(1, "C1-P1-Mimosa-UHT Magro-1L", 1, 1, "Leite", "Mimosa", "1L", "UHT Magro", 2, "Leite Magro - Bem Essencial", "Frigorífico"),
                StockItemDTO(1, "C1-P1-Mimosa-UHT Meio Gordo-1L", 1, 1, "Leite", "Mimosa", "1L", "UHT Meio Gordo", 1, "Leite Meio Gordo - Bem Essencial", "Frigorífico"),
                StockItemDTO(1, "C1-P2-Danone-Natural Açucarado-100mg", 1, 2, "Iogurte", "Danone", "100mg", "Natural Açucarado", 4, "Danone os iogurtes não sei que", "Frigorífico")
        )
    }

    private fun onSuccess(stockItems: StockItemsDto) {
        // Set spinner options
        // TODO falta ir buscar a casa
        /* val spinnerAdapter = ArrayAdapter<String>(view?.context, android.R.layout.simple_spinner_item, houses.map { house -> house.name })
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.housesSpinner.adapter = spinnerAdapter
        view.housesSpinner.setSelection(first) */

        // Set Adapter
        stockItemListAdapter = StockItemListAdapter(stockItems.stockItems)
        view?.let {
            it.stockItemListRecyclerView.layoutManager = LinearLayoutManager(it.context)
            it.stockItemListRecyclerView.setHasFixedSize(true)
            it.stockItemListRecyclerView.adapter = stockItemListAdapter
            // Set listener for add stock item
            it.addStockItemBtn.setOnClickListener {
                listener?.onNewStockItemIteraction()
            }
        }
        stockItemListAdapter.setOnItemClickListener(this)
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_item_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.in_stock)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnStockItemListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnStockItemListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Listeners
     ***/

    override fun onItemClick(view: View, position: Int) {
        val stockItem: StockItemDTO = stockItems[position]
        listener?.onStockItemInteraction(stockItem)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Nothing to do here
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.let {
            if (it.housesSpinner.selectedItem != position) {
                val houseId = houses[position].houseId
                //TODO: get data
                stockItemListAdapter.setData(/*stockItems*/arrayOf())
                stockItemListAdapter.notifyDataSetChanged()
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
        fun onStockItemInteraction(stockItem: StockItemDTO)
        fun onNewStockItemIteraction()
    }

    /**
     * StcokItemListFragment Factory
     */
    companion object {
        val usernameArg: String = "username"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param args Arguments
         * @return A new instance of fragment StockItemListFragment.
         */
        @JvmStatic
        fun newInstance(args: Map<String, Any>) =
                StockItemListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExtraUtils.USER_USERNAME, args[usernameArg] as String)
                    }
                }
    }
}
