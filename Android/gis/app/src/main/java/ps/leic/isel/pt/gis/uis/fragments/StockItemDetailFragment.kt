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
import kotlinx.android.synthetic.main.fragment_stock_item_detail.*
import kotlinx.android.synthetic.main.fragment_stock_item_detail.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.*
import ps.leic.isel.pt.gis.model.dtos.StockItemDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.StockItemDetailsExpirationDateAdapter
import ps.leic.isel.pt.gis.uis.adapters.StockItemDetailsMovementsAdapter
import ps.leic.isel.pt.gis.uis.adapters.StockItemDetailsStorageAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.getElementsSeparatedBySemiColon
import ps.leic.isel.pt.gis.viewModel.StockItemDetailViewModel

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
    private var stockItemDetailViewModel: StockItemDetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stockItem = it.getParcelable(ExtraUtils.STOCK_ITEM)
        }
        stockItemDetailViewModel = ViewModelProviders.of(this).get(StockItemDetailViewModel::class.java)
        val url = ""
        stockItemDetailViewModel?.init(url)
        stockItemDetailViewModel?.getStockItem()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
        //TODO: get data
    }

    private fun onSuccess(stockItem: StockItemDto) {
        // Set Allergens
        allergensText.text = allergens.getElementsSeparatedBySemiColon()

        // Set Adapters (Expiration dates)
        val expirationDatesAdapter = StockItemDetailsExpirationDateAdapter(/*stockItem.expirationsDate*/arrayOf())
        // Set Adapter (Storages)
        val storagesAdapter = StockItemDetailsStorageAdapter(/*stockItem.storages*/arrayOf())
        // Set Adapter (Movements)
        val movementsAdapter = StockItemDetailsMovementsAdapter(/*stockItem.movements*/arrayOf())
        view?.let {
            // Set Adapters (Expiration dates)
            it.expirationDateRecyclerView.layoutManager = LinearLayoutManager(it.context)
            it.expirationDateRecyclerView.setHasFixedSize(true)
            it.expirationDateRecyclerView.adapter = expirationDatesAdapter
            // Set Adapter (Storages)
            it.storageRecyclerView.layoutManager = LinearLayoutManager(it.context)
            it.storageRecyclerView.setHasFixedSize(true)
            it.storageRecyclerView.adapter = storagesAdapter
            // Set Description
            it.descriptionText.text = stockItem.description
            // Set Adapter (Movements)
            it.movementsRecyclerView.layoutManager = LinearLayoutManager(it.context)
            it.movementsRecyclerView.setHasFixedSize(true)
            it.movementsRecyclerView.adapter = movementsAdapter
        }
        storagesAdapter.setOnItemClickListener(this)
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_item_detail, container, false)
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
