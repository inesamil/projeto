package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.layout_add_list_product_dialog.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListProduct
import ps.leic.isel.pt.gis.model.dtos.*
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.AddProductToListAdapter
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.viewModel.CategoriesViewModel
import ps.leic.isel.pt.gis.viewModel.CategoryProductsViewModel

class AddProductToListDialogFragment : DialogFragment(), AddProductToListAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var url: String

    private val FIRST: Int = 0
    private var previousSelectedCategoryPosition: Int = FIRST

    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var categoryProductsViewModel: CategoryProductsViewModel

    private val adapter: AddProductToListAdapter = AddProductToListAdapter()

    private var categories: Array<CategoryDto>? = null
    private var products: Array<ProductDto>? = null

    private var state: State = State.LOADING

    private lateinit var categorySpinner: Spinner
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ScrollView

    private var listener: OnAddProductToListDialogFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAddProductToListDialogFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnNewListDialogFragmentInteractionListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        arguments?.let {
            url = it.getString(URL_TAG)
        }

        getCategories(url)

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(R.layout.layout_add_list_product_dialog, null)
        builder.setView(view)

        categorySpinner = view.categoriesSpinner

        // Set Adapter to Recycler View
        view.productsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.productsRecyclerView.setHasFixedSize(true)
        view.productsRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        progressBar = view.listProductDialogProgressBar
        content = view.productsScrollView

        showProgressBarOrContent()

        return builder.create()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun getCategories(url: String) {
        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        categoriesViewModel.init(url)
        categoriesViewModel.getCategories()?.observe(this, Observer {
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

    private fun onSuccess(categoriesDto: CategoriesDto?) {
        categoriesDto?.let {
            categories = it.categories

            categories?.let {
                val spinnerAdapter = ArrayAdapter<String>(categorySpinner.context, android.R.layout.simple_spinner_item, it.map { category -> category.categoryName })
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                categorySpinner.adapter = spinnerAdapter
                categorySpinner.onItemSelectedListener = this
                categorySpinner.setSelection(FIRST)
            }

            val size = categories?.size ?: 0
            if (size > 0) {
                categories?.get(FIRST)?.links?.productsCategoryLink?.let {
                    getProductsCategory(it)
                }
            }
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

    private fun getProductsCategory(url: String) {
        categoryProductsViewModel = ViewModelProviders.of(this).get(CategoryProductsViewModel::class.java)
        categoryProductsViewModel.init(url)
        categoryProductsViewModel.getProducts()?.observe(this, Observer {
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

    private fun onSuccess(productsDto: ProductsDto?) {
        productsDto?.let {
            state = State.SUCCESS

            // Show progress bar or content
            showProgressBarOrContent()

            products = it.products

            products?.let {
                adapter.setData(it)
            }
        }
    }

    private fun showProgressBarOrContent() {
        progressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
        content.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
    }

    /**
     * Listeners
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.let {
            if (previousSelectedCategoryPosition != position) {
                previousSelectedCategoryPosition = position
                categories?.let {
                    it[position].links.productsCategoryLink?.let {
                        getProductsCategory(it)
                    }
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Nothing to do here
    }

    override fun onItemActionClick(listProduct: ListProduct) {
        if (listProduct.quantity > 0) {
            listener?.onAddProductToList(listProduct)
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnAddProductToListDialogFragmentInteractionListener {
        fun onAddProductToList(listProduct: ListProduct)
    }

    /**
     * AddProductToListDialogFragment Factory
     */
    companion object {
        const val TAG: String = "AddListProductFragment"
        private const val URL_TAG: String = "URL"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NewListDialogFragment.
         */
        @JvmStatic
        fun newInstance(url: String) = AddProductToListDialogFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(URL_TAG, url)
                    }
                }
    }
}