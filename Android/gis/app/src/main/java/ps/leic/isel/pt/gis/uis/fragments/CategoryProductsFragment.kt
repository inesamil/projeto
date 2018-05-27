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
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_category_products.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.CategoryDTO
import ps.leic.isel.pt.gis.model.ProductDTO
import ps.leic.isel.pt.gis.model.dtos.CategoryDto
import ps.leic.isel.pt.gis.model.dtos.ProductDto
import ps.leic.isel.pt.gis.model.dtos.ProductsDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.CategoryProductsAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
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
class CategoryProductsFragment : Fragment(), CategoryProductsAdapter.OnItemClickListener {

    private lateinit var category: CategoryDto
    private lateinit var products: ProductsDto

    private var listener: OnCategoryProductsFragmentInteractionListener? = null
    private var categoryProductsViewModel: CategoryProductsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //TODO category = it.getParcelable(ExtraUtils.CATEGORY)

        }
        categoryProductsViewModel = ViewModelProviders.of(this).get(CategoryProductsViewModel::class.java)
        val url = ""
        categoryProductsViewModel?.init(url)
        categoryProductsViewModel?.getProducts()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
        //TODO: get data
       /* products = arrayOf(
                ProductDTO(1, 1, "Leite", true, "3dias"),
                ProductDTO(1, 2, "Queijo", true, "7dias"),
                ProductDTO(1, 3, "Iogurte", true, "20dias")
        )*/
    }

    private fun onSuccess(products: ProductsDto) {
        // Set Adapter
        val adapter = CategoryProductsAdapter(products.products)
        view?.let {
            it.categoryProductsRecyclerView.layoutManager = LinearLayoutManager(it.context)
            it.categoryProductsRecyclerView.setHasFixedSize(true)
            it.categoryProductsRecyclerView.adapter = adapter
        }
        adapter.setOnItemClickListener(this)
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_products, container, false)
    }

    override fun onStart() {
        super.onStart()
        activity?.title = category.categoryName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCategoryProductsFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnCategoryProductsFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Listeners
     ***/

    override fun onTextClick(view: View, position: Int) {
        val product: ProductDto = products.products[position]
        listener?.onProductInteraction(product)
    }

    override fun onPlusClick(view: View, position: Int) {
        Toast.makeText(view.context, "Add Product to a List.", Toast.LENGTH_LONG).show()
        //TODO
    }

    override fun onMinusClick(view: View, position: Int) {
        Toast.makeText(view.context, "Remove Product from a List.", Toast.LENGTH_LONG).show()
        //TODO
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnCategoryProductsFragmentInteractionListener {
        fun onProductInteraction(product: ProductDto)
    }

    /**
     * CategoryProductsFragment Factory
     */
    companion object {
        val categoryArg: String = "category"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param category Category
         * @return A new instance of fragment CategoryProductsFragment.
         */

        @JvmStatic
        fun newInstance(args: Map<String, Any>) =
                CategoryProductsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ExtraUtils.CATEGORY, args[categoryArg] as CategoryDTO)
                    }
                }
    }
}
