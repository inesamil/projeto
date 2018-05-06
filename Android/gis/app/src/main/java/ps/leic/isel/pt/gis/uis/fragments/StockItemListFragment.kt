package ps.leic.isel.pt.gis.uis.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_stock_item_list.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.StockItemDTO
import ps.leic.isel.pt.gis.uis.adapters.StockItemListAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StockItemListFragment.OnStockItemListFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StockItemListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StockItemListFragment : Fragment(), StockItemListAdapter.OnItemClickListener {

    private var houseId: Long = 0
    private lateinit var stockItems: Array<StockItemDTO>

    private var listener: OnStockItemListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            houseId = it.getLong(ExtraUtils.HOUSE_ID)
        }
        //TODO: Get data
        //if (houseId != 0)
        stockItems = arrayOf(
                StockItemDTO(1, "C1-P1-Mimosa-UHT Magro-1L", 1, 1, "Leite", "Mimosa", "1L", "UHT Magro", 2, "Leite Magro - Bem Essencial", "Frigorífico"),
                StockItemDTO(1, "C1-P1-Mimosa-UHT Meio Gordo-1L", 1, 1, "Leite", "Mimosa", "1L", "UHT Meio Gordo", 1, "Leite Meio Gordo - Bem Essencial", "Frigorífico"),
                StockItemDTO(1, "C1-P2-Danone-Natural Açucarado-100mg", 1, 2, "Iogurte", "Danone", "100mg", "Natural Açucarado", 4, "Danone os iogurtes não sei que", "Frigorífico")
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_stock_item_list, container, false)

        // Set Adapter
        val adapter = StockItemListAdapter(stockItems)
        view.stockItemListRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.stockItemListRecyclerView.setHasFixedSize(true)
        view.stockItemListRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        return view
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnStockItemListFragmentInteractionListener {
        fun onStockItemInteraction(stockItem: StockItemDTO)
    }

    /**
     * StcokItemListFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param houseId House ID
         * @return A new instance of fragment StockItemListFragment.
         */
        @JvmStatic
        fun newInstance(houseId: Long) =
                StockItemListFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ExtraUtils.HOUSE_ID, houseId)
                    }
                }
    }
}
