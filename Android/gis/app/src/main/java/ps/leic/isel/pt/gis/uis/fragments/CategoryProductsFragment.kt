package ps.leic.isel.pt.gis.uis.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_categoryproducts.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ProductDTO
import ps.leic.isel.pt.gis.model.ids.CategoryID
import ps.leic.isel.pt.gis.model.ids.ProductID
import ps.leic.isel.pt.gis.uis.adapters.CategoryProductsAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils

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

    private lateinit var categoryId: CategoryID
    private lateinit var categoryName: String
    private lateinit var products: Array<ProductDTO>

    private var listener: OnCategoryProductsFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getParcelable(ExtraUtils.CATEGORY_ID)
            categoryName = it.getString(ExtraUtils.CATEGORY_NAME)

        }
        //TODO: get data
        products = arrayOf(
                ProductDTO(1,  1, "Leite", true, "3dias"),
                ProductDTO(1, 2, "Queijo", true, "7dias"),
                ProductDTO(1, 3, "Iogurte", true, "20dias")
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_categoryproducts, container, false)

        // Set Title
        view.categoryText.text = categoryName

        // Set Adapter
        val adapter = CategoryProductsAdapter(products)
        view.categoryProductsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.categoryProductsRecyclerView.setHasFixedSize(true)
        view.categoryProductsRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        return view
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
        val product: ProductDTO = products[position]
        listener?.onProductInteraction(ProductID(product.categoryId, product.productId))
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
        fun onProductInteraction(productId: ProductID)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param categoryId Category ID
         * @param categoryName Category Name (This fragment title)
         * @return A new instance of fragment CategoryProductsFragment.
         */

        @JvmStatic
        fun newInstance(categoryId: CategoryID, categoryName: String) =
                CategoryProductsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ExtraUtils.CATEGORY_ID, categoryId)
                        putString(ExtraUtils.CATEGORY_NAME, categoryName)
                    }
                }
    }
}