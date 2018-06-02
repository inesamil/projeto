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
import ps.leic.isel.pt.gis.model.dtos.StockItemDto
import ps.leic.isel.pt.gis.model.dtos.StorageDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.StockItemDetailsExpirationDateAdapter
import ps.leic.isel.pt.gis.uis.adapters.StockItemDetailsMovementsAdapter
import ps.leic.isel.pt.gis.uis.adapters.StockItemDetailsStorageAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
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

    private val expirationDatesAdapter = StockItemDetailsExpirationDateAdapter()
    private val storagesAdapter = StockItemDetailsStorageAdapter()
    private val movementsAdapter = StockItemDetailsMovementsAdapter()

    private var storages: Array<StorageDto>? = null

    private var listener: OnStockItemDetailFragmentInteractionListener? = null
    private lateinit var stockItemDetailViewModel: StockItemDetailViewModel

    private lateinit var url: String
    private lateinit var productName: String
    private lateinit var variety: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnStockItemDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnStockItemDetailFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ExtraUtils.URL)
            productName = it.getString(ExtraUtils.PRODUCT_NAME)
            variety = it.getString(ExtraUtils.VARIETY)
        }
        stockItemDetailViewModel = ViewModelProviders.of(this).get(StockItemDetailViewModel::class.java)
        stockItemDetailViewModel.init(url)
        stockItemDetailViewModel.getStockItem()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
    }

    private fun onSuccess(stockItem: StockItemDto) {
        // Hide progress bar
        stockItemDetailProgressBar.visibility = View.GONE

        // Set Allergens
        //TODO allergensText.text = allergens.getElementsSeparatedBySemiColon()

        // Set Adapters (Expiration dates)
        expirationDatesAdapter.setData(stockItem.expirationDates)

        // Set Adapter (Storages)
        storagesAdapter.setData(stockItem.storages)
        // TODO stockItem.storages é um array de string e para o listener é preciso um array de storage dto
        // this.storages = stockItem.storages

        // Set Adapter (Movements)
        movementsAdapter.setData(stockItem.movements)

        // Set Description TODO verificar se a kotlin extension funciona senão é precisar adicionar view?.
        descriptionText.text = stockItem.description
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stock_item_detail, container, false)

        // Set Adapters (Expiration dates)
        view.expirationDateRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.expirationDateRecyclerView.setHasFixedSize(true)
        view.expirationDateRecyclerView.adapter = expirationDatesAdapter

        // Set Adapter (Storages)
        view.storageRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.storageRecyclerView.setHasFixedSize(true)
        view.storageRecyclerView.adapter = storagesAdapter

        // Set Adapter (Movements)
        view.movementsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.movementsRecyclerView.setHasFixedSize(true)
        view.movementsRecyclerView.adapter = movementsAdapter

        storagesAdapter.setOnItemClickListener(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(ExtraUtils.URL)
            productName = it.getString(ExtraUtils.PRODUCT_NAME)
            variety = it.getString(ExtraUtils.VARIETY)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = "$productName $variety"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ExtraUtils.URL, url)
        outState.putString(ExtraUtils.PRODUCT_NAME, productName)
        outState.putString(ExtraUtils.VARIETY, variety)
    }

    override fun onStop() {
        super.onStop()
        stockItemDetailViewModel.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Listeners
     ***/

    // Listener for storage item clicks (from adapter)
    override fun onItemClick(view: View, position: Int) {
        storages?.let {
            val storage = it[position]
            listener?.onStorageInteraction(storage)
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnStockItemDetailFragmentInteractionListener {
        fun onStorageInteraction(storage: StorageDto)
    }

    /**
     * StockItemDetailFragment Factory
     */
    companion object {
        const val URL_ARG = "url"
        const val PRODUCT_NAME_ARG = "product-name"
        const val STOCK_ITEM_VARIETY_ARG = "variety"

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
                        putString(ExtraUtils.URL, args[URL_ARG] as String)
                        putString(ExtraUtils.PRODUCT_NAME, args[PRODUCT_NAME_ARG] as String)
                        putString(ExtraUtils.VARIETY, args[STOCK_ITEM_VARIETY_ARG] as String)
                    }
                }
    }
}
