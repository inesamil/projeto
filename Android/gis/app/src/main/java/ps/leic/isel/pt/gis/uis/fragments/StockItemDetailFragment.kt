package ps.leic.isel.pt.gis.uis.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_stock_item_detail.*
import kotlinx.android.synthetic.main.fragment_stock_item_detail.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.*
import ps.leic.isel.pt.gis.uis.adapters.StockItemDetailsExpirationDateAdapter
import ps.leic.isel.pt.gis.uis.adapters.StockItemDetailsMovementsAdapter
import ps.leic.isel.pt.gis.uis.adapters.StockItemDetailsStorageAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.getElementsSeparatedBySemiColon

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StockItemDetailFragment.OnStockItemDetailFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StockItemDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StockItemDetailFragment : Fragment(), StockItemDetailsStorageAdapter.OnItemClickListener {

    private lateinit var stockItem: StockItemDTO
    private lateinit var allergens: Array<StockItemAllergenDTO>
    private lateinit var expirationDates: Array<ExpirationDateDTO>
    private lateinit var storages: Array<StorageDTO>
    private lateinit var movements: Array<MovementDTO>

    private var listener: OnStockItemDetailFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stockItem = it.getParcelable(ExtraUtils.STOCK_ITEM)
        }
        //TODO: get data
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_stock_item_detail, container, false)

        // Set Allergens
        allergensText.text = allergens.getElementsSeparatedBySemiColon()

        // Set Adapters (Expiration dates)
        val expirationDatesAdapter = StockItemDetailsExpirationDateAdapter(expirationDates)
        view.expirationDateRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.expirationDateRecyclerView.setHasFixedSize(true)
        view.expirationDateRecyclerView.adapter = expirationDatesAdapter

        // Set Adapter (Storages)
        val storagesAdapter = StockItemDetailsStorageAdapter(storages)
        view.storageRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.storageRecyclerView.setHasFixedSize(true)
        view.storageRecyclerView.adapter = storagesAdapter
        storagesAdapter.setOnItemClickListener(this)

        // Set Description
        view.descriptionText.text = stockItem.description

        // Set Adapter (Movements)
        val movementsAdapter = StockItemDetailsMovementsAdapter(movements)
        view.movementsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.movementsRecyclerView.setHasFixedSize(true)
        view.movementsRecyclerView.adapter = movementsAdapter

        return view
    }

    override fun onStart() {
        super.onStart()
        activity?.title = stockItem.productName + " " + stockItem.variety
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnStockItemDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnStockItemDetailFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Listeners
     ***/

    // NfcListener for storage item clicks (from adapter)
    override fun onItemClick(view: View, position: Int) {
        val storage: StorageDTO = storages[position]
        listener?.onStorageInteraction(storage)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnStockItemDetailFragmentInteractionListener {
        fun onStorageInteraction(storage: StorageDTO)
    }

    /**
     * StockItemDetailFragment Factory
     */
    companion object {
        val stockItemArg: String = "stockitem"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param args Arguments
         * @return A new instance of fragment StockItemDetailFragment.
         */
        @JvmStatic
        fun newInstance(args: Map<String, Any>) =
                StockItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ExtraUtils.STOCK_ITEM, args[stockItemArg] as StockItemDTO)
                    }
                }
    }
}
