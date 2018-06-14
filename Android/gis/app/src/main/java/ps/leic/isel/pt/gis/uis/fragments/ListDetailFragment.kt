package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_list.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ListProductDto
import ps.leic.isel.pt.gis.model.dtos.ProductsListDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.ListDetailAdapter
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.viewModel.ListDetailViewModel

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ListDetailFragment.OnListDetailFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ListDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ListDetailFragment : Fragment(), ListDetailAdapter.OnItemClickListener {

    private var listName: String? = null
    private var listProducts: Array<ListProductDto>? = null

    private var listener: OnListDetailFragmentInteractionListener? = null
    private lateinit var listDetailViewModel: ListDetailViewModel
    private lateinit var url: String
    private val adapter = ListDetailAdapter()

    private var state: State = State.LOADING
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ConstraintLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListDetailFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL_KEY)
            listName = it.getString(LIST_NAME_KEY)
        }
        listDetailViewModel = ViewModelProviders.of(this).get(ListDetailViewModel::class.java)
        listDetailViewModel.init(url)
        listDetailViewModel.getListDetail()?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> onSuccess(it.data!!)
                it?.status == Status.ERROR -> onError(it.message)
                it?.status == Status.LOADING -> {
                    state = State.LOADING
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // Set Adapter to Recycler View
        view.listRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.listRecyclerView.setHasFixedSize(true)
        view.listRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        // Set listener to transferToOfflineLayout clicks
        view.transferToOfflineLayout.setOnClickListener {
            listener?.onListDownload()
        }

        progressBar = view.listProgressBar
        content = view.listLayout

        // Show progress bar or content
        showProgressBarOrContent()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(URL_KEY)
            listName = it.getString(LIST_NAME_KEY)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = listName
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL_KEY, url)
        outState.putString(LIST_NAME_KEY, listName)
    }

    override fun onStop() {
        super.onStop()
        listDetailViewModel.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Auxiliary Methods
     ***/

    private fun onSuccess(productsList: ProductsListDto) {
        state = State.SUCCESS

        // Show progress bar or content
        showProgressBarOrContent()

        adapter.setData(productsList.productsListProduct)
        this.listProducts = productsList.productsListProduct
    }

    private fun onError(error: String?) {
        state = State.ERROR
        error?.let {
            Log.v("APP_GIS", error)
        }
    }

    private fun showProgressBarOrContent() {
        progressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
        content.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
    }

    /***
     * Listeners
     ***/

    // NfcListener for list item clicks (from adapter)
    override fun onItemClick(view: View, position: Int) {
        listProducts?.let {
            val listProduct = it[position]
            listener?.onListProductInteraction(listProduct)
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnListDetailFragmentInteractionListener {
        fun onListProductInteraction(listProduct: ListProductDto)
        fun onListDownload()    //TODO: o que é preciso passar?
    }

    /**
     * ListDetailFragment Factory
     */
    companion object {
        const val TAG: String = "ListDetailFragment"
        private const val URL_KEY: String = "URL"
        const val URL_ARG = "url"
        private const val LIST_NAME_KEY: String = "LIST_NAME"
        const val LIST_NAME_ARG = "list-name"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param args Arguments
         * @return A new instance of fragment ListDetailFragment.
         */
        @JvmStatic
        fun newInstance(args: Map<String, Any>) =
                ListDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(URL_KEY, args[URL_ARG] as String)
                        putString(LIST_NAME_KEY, args[LIST_NAME_ARG] as String)
                    }
                }
    }
}
