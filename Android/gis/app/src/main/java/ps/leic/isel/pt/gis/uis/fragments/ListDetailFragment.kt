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
import kotlinx.android.synthetic.main.fragment_list.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListProduct
import ps.leic.isel.pt.gis.model.body.ListProductBody
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.ListDto
import ps.leic.isel.pt.gis.model.dtos.ListProductDto
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
    private var list: ListDto? = null

    private var listener: OnListDetailFragmentInteractionListener? = null
    private lateinit var listDetailViewModel: ListDetailViewModel
    private lateinit var url: String
    private val adapter = ListDetailAdapter()
    private var listProducts: Array<ListProductDto>? = null

    private var state: State = State.LOADING
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ConstraintLayout
    private lateinit var emptyLayout: ConstraintLayout

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
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // Set Adapter to Recycler View
        view.listRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.listRecyclerView.setHasFixedSize(true)
        view.listRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        progressBar = view.listProgressBar
        content = view.listLayout
        emptyLayout = view.emptyLayout

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

    private fun onSuccess(list: ListDto?) {
        list?.let {
            state = State.SUCCESS

            this.list = list

            // Show progress bar or content
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
        when (state) {
            State.LOADING -> {
                progressBar.visibility = View.VISIBLE
                content.visibility = View.INVISIBLE
            }
            State.SUCCESS -> {
                progressBar.visibility = View.GONE
                content.visibility = View.VISIBLE
                // Show list details
                showUserListDetailOrSystemListDetail()
                // Show list products or hint
                list?.listProducts?.let {
                    if (it.isEmpty()) {
                        emptyLayout.visibility = View.VISIBLE
                    } else {
                        emptyLayout.visibility = View.GONE
                        listProducts = it
                        adapter.setData(it)
                    }
                }
            }
        }
    }

    private fun showUserListDetailOrSystemListDetail() {
        list?.let {
            // Set house name
            content.houseNameText.text = it.houseName

            if (it.listType == ListDto.USER_TYPE) {
                content.userListDetails.visibility = View.VISIBLE
                content.systemListDetails.visibility = View.GONE
                content.addProductButton.visibility = View.VISIBLE

                // Set username
                content.usernameText.text = it.username
                // Set private list checkbox
                it.shareable?.let {
                    content.privateListCheckbox.isChecked = !it
                }

                // Set add product listener
                content.addProductButton.setOnClickListener {
                    listener?.onAddProductToListInteraction()
                }
            } else {
                content.userListDetails.visibility = View.GONE
                content.systemListDetails.visibility = View.VISIBLE
                content.addProductButton.visibility = View.GONE
            }
        }
    }

    /***
     * Listeners
     ***/

    fun onAddProductToList(listProduct: ListProduct) {
        listDetailViewModel.addProductToList(ListProductBody(listProduct.productId, listProduct.brand, listProduct.quantity))?.observe(this, Observer {
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

    fun onListProductEdited(listProduct: ListProduct) {
        listDetailViewModel.updateListProduct(ListProductBody(listProduct.productId, listProduct.brand, listProduct.quantity))?.observe(this, Observer {
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

    fun onListProductRemoved(listProduct: ListProductDto) {
        listDetailViewModel.removeListProduct(ListProductBody(listProduct.productId, listProduct.productListBrand, listProduct.productsListQuantity))?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> {
                    onSuccess(it.data)
                    Toast.makeText(context, getString(R.string.list_product_removed_successfully), Toast.LENGTH_SHORT).show()
                }
                it?.status == Status.ERROR -> {
                    Toast.makeText(context, getString(R.string.could_not_remove_list_product), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onEditClick(listProductDto: ListProductDto) {
        listener?.onEditListProductInteraction(listProductDto)
    }

    override fun onDeleteClick(listProductDto: ListProductDto) {
        listener?.onDeleteListProductInteraction(listProductDto)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnListDetailFragmentInteractionListener {
        fun onAddProductToListInteraction()
        fun onEditListProductInteraction(listProduct: ListProductDto)
        fun onDeleteListProductInteraction(listProduct: ListProductDto)
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
