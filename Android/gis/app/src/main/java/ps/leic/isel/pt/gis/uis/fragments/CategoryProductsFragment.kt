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
import kotlinx.android.synthetic.main.fragment_category_products.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.ProductDto
import ps.leic.isel.pt.gis.model.dtos.ProductsDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.activities.HomeActivity
import ps.leic.isel.pt.gis.uis.adapters.CategoryProductsAdapter
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.viewModel.CategoryProductsViewModel


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CategoryProductsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CategoryProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CategoryProductsFragment : Fragment() {

    private lateinit var categoryName: String
    private var products: Array<ProductDto>? = null

    private val adapter = CategoryProductsAdapter()

    private lateinit var categoryProductsViewModel: CategoryProductsViewModel
    private lateinit var url: String

    private var state: State = State.LOADING
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL_TAG)
            categoryName = it.getString(CATEGORY_NAME_TAG)
        }
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category_products, container, false)

        // Set Adapter to Recycler View
        view.categoryProductsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.categoryProductsRecyclerView.setHasFixedSize(true)
        view.categoryProductsRecyclerView.adapter = adapter

        progressBar = view.categoryProductsProgressBar
        content = view.categoryProductsLayout

        // Show progress bar or content
        showProgressBarOrContent()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(URL_TAG)
            categoryName = it.getString(CATEGORY_NAME_TAG)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = categoryName
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL_TAG, url)
        outState.putString(CATEGORY_NAME_TAG, categoryName)
    }

    override fun onStop() {
        super.onStop()
        categoryProductsViewModel.cancel()
    }

    /***
     * Auxiliar Methods
     ***/
    private fun onSuccess(products: ProductsDto?) {
        products?.let {
            state = State.SUCCESS

            // Hide progress bar
            showProgressBarOrContent()

            // Set Adapter
            adapter.setData(it.products)
            this.products = it.products
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

    /**
     * CategoryProductsFragment Factory
     */
    companion object {
        const val TAG: String = "ProductsFragment"
        private const val URL_TAG = "URL"
        private const val CATEGORY_NAME_TAG = "CATEGORY-NAME"
        const val URL_ARG = "url"
        const val CATEGORY_NAME_ARG = "category-name"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param args Arguments
         * @return A new instance of fragment CategoryProductsFragment.
         */
        @JvmStatic
        fun newInstance(args: Map<String, Any>) =
                CategoryProductsFragment().apply {
                    arguments = Bundle().apply {
                        putString(URL_TAG, args[URL_ARG] as String)
                        putString(CATEGORY_NAME_TAG, args[CATEGORY_NAME_ARG] as String)
                    }
                }
    }
}
