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
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_stock_item_detail.*
import kotlinx.android.synthetic.main.fragment_stock_item_detail.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.StockItemDto
import ps.leic.isel.pt.gis.model.dtos.StorageDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.StockItemDetailsExpirationDateAdapter
import ps.leic.isel.pt.gis.uis.adapters.StockItemDetailsMovementsAdapter
import ps.leic.isel.pt.gis.uis.adapters.StockItemDetailsStorageAdapter
import ps.leic.isel.pt.gis.utils.State
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

    private var state: State = State.LOADING
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ConstraintLayout

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
            url = it.getString(URL_KEY)
            productName = it.getString(PRODUCT_NAME_KEY)
            variety = it.getString(STOCK_ITEM_VARIETY_KEY)
        }
        stockItemDetailViewModel = ViewModelProviders.of(this).get(StockItemDetailViewModel::class.java)
        stockItemDetailViewModel.init(url)
        stockItemDetailViewModel.getStockItem()?.observe(this, Observer {
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

        progressBar = view.stockItemDetailProgressBar
        content = view.stockItemDetailLayout

        // Show progress bar or content
        showProgressBarOrContent()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(URL_KEY)
            productName = it.getString(PRODUCT_NAME_KEY)
            variety = it.getString(STOCK_ITEM_VARIETY_KEY)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = "$productName $variety"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL_KEY, url)
        outState.putString(PRODUCT_NAME_KEY, productName)
        outState.putString(STOCK_ITEM_VARIETY_KEY, variety)
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
     * Auxiliary Methods
     ***/

    private fun onSuccess(stockItem: StockItemDto?) {
        stockItem?.let {
            state = State.SUCCESS

            // Show progress bar or content
            showProgressBarOrContent()

            brandText.text = it.brand
            quantityText.text = it.quantity.toString()
            unitText.text = it.segment

            // Set Allergens
            allergensText.text = it.allergens?.joinToString(";")

            // Set Adapters (Expiration dates)
            expirationDatesAdapter.setData(it.expirationDates)

            // Set Adapter (Storages)
            storagesAdapter.setData(it.storages)
            // TODO stockItem.storages é um array de string e para o listener é preciso um array de storage dto
            // this.storages = stockItem.storages

            // Set Adapter (Movements)
            movementsAdapter.setData(it.movements)

            // Set Description
            descriptionText.text = it.description
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
        view?.let {
            it.stockItemDetailProgressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
            it.stockItemDetailLayout.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
        }
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
        const val TAG: String = "StockItemDetailFragment"
        private const val URL_KEY: String = "URL"
        const val URL_ARG = "url"
        private const val PRODUCT_NAME_KEY: String = "PRODUCT_NAME"
        const val PRODUCT_NAME_ARG = "product-name"
        private const val STOCK_ITEM_VARIETY_KEY: String = "VARIETY"
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
                        putString(URL_KEY, args[URL_ARG] as String)
                        putString(PRODUCT_NAME_KEY, args[PRODUCT_NAME_ARG] as String)
                        putString(STOCK_ITEM_VARIETY_KEY, args[STOCK_ITEM_VARIETY_ARG] as String)
                    }
                }
    }
}
