package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_categories.view.*
import kotlinx.android.synthetic.main.fragment_category_products.*
import kotlinx.android.synthetic.main.fragment_category_products.view.*
import kotlinx.android.synthetic.main.layout_add_list_product_popup.*
import kotlinx.android.synthetic.main.layout_add_list_product_popup.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ListDto
import ps.leic.isel.pt.gis.model.dtos.ProductDto
import ps.leic.isel.pt.gis.model.dtos.ProductsDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.CategoryProductsAdapter
import ps.leic.isel.pt.gis.uis.adapters.ListsPopupAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.viewModel.CategoryProductsViewModel
import ps.leic.isel.pt.gis.viewModel.ListsViewModel


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CategoryProductsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CategoryProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CategoryProductsFragment : Fragment(), CategoryProductsAdapter.OnItemClickListener {

    private lateinit var categoryName: String
    private var products: Array<ProductDto>? = null

    private var listener: OnCategoryProductsFragmentInteractionListener? = null
    private val adapter = CategoryProductsAdapter()

    private lateinit var listsViewModel: ListsViewModel
    private lateinit var categoryProductsViewModel: CategoryProductsViewModel
    private lateinit var url: String

    private var state: State = State.LOADING;

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCategoryProductsFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnCategoryProductsFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ExtraUtils.URL)
            categoryName = it.getString(ExtraUtils.CATEGORY_NAME)
        }
        categoryProductsViewModel = ViewModelProviders.of(this).get(CategoryProductsViewModel::class.java)
        categoryProductsViewModel.init(url)
        categoryProductsViewModel.getProducts()?.observe(this, Observer {
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
        val view = inflater.inflate(R.layout.fragment_category_products, container, false)

        // Set Adapter to Recycler View
        view.categoryProductsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.categoryProductsRecyclerView.setHasFixedSize(true)
        view.categoryProductsRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        // Show progress bar or content
        showProgressBarOrContent()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(ExtraUtils.URL)
            categoryName = it.getString(ExtraUtils.CATEGORY_NAME)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = categoryName
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ExtraUtils.URL, url)
        outState.putString(ExtraUtils.CATEGORY_NAME, categoryName)
    }

    override fun onStop() {
        super.onStop()
        categoryProductsViewModel.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Auxiliar Methods
     ***/
    private fun onSuccess(products: ProductsDto) {
        state = State.SUCCESS

        // Show progress bar or content
        showProgressBarOrContent()

        // Set Adapter
        adapter.setData(products.products)
        this.products = products.products
    }

    private fun onError(error: String?) {
        state = State.ERROR
        error?.let {
            Log.v("APP_GIS", it)
        }
    }

    private fun showProgressBarOrContent() {
        view?.let {
            it.categoryProductsProgressBar?.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
            it.categoriesLayout?.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun showAddPopup(v: View, lists: Array<ListDto>) {
        val location = IntArray(2)

        v.getLocationOnScreen(location)

        // Inflate the popup_layout.xml
        val layout = layoutInflater.inflate(R.layout.layout_add_list_product_popup, popupLayout)

        // Set Adapter to Recycler View
        layout.listsRecyclerView.layoutManager = LinearLayoutManager(layout.context)
        layout.listsRecyclerView.setHasFixedSize(true)
        layout.listsRecyclerView.adapter = ListsPopupAdapter(lists)

        // Creating the PopupWindow
        val popup = PopupWindow(context)
        popup.contentView = layout
        popup.isFocusable = true

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        val OFFSET_X = 30
        val OFFSET_Y = 30

        // Clear the default translucent background
        //popup.setBackgroundDrawable(BitmapDrawable())

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, location[0] + OFFSET_X, location[1] + OFFSET_Y)

        // Getting a reference to Close button, and close the popup when clicked.
        layout.cancelButton.setOnClickListener {
            popup.dismiss()
        }

    }

    /***
     * Listeners
     ***/

    override fun onTextClick(view: View, position: Int) {
        products?.let {
            val product: ProductDto = it[position]
            product.links.selfLink?.let {
                listener?.onProductInteraction(it, product.productName)
            }
        }
    }

    override fun onPlusClick(view: View, position: Int) {
        val lists: Array<ListDto> = arrayOf()
        showAddPopup(view, lists)
        Toast.makeText(context, "Add Product to a List.", Toast.LENGTH_LONG).show()
        //TODO
    }

    override fun onMinusClick(view: View, position: Int) {
        Toast.makeText(context, "Remove Product from a List.", Toast.LENGTH_LONG).show()
        //TODO
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnCategoryProductsFragmentInteractionListener {
        fun onProductInteraction(url: String, productName: String)
    }

    /**
     * CategoryProductsFragment Factory
     */
    companion object {
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
                        putString(ExtraUtils.URL, args[URL_ARG] as String)
                        putString(ExtraUtils.CATEGORY_NAME, args[CATEGORY_NAME_ARG] as String)
                    }
                }
    }
}
