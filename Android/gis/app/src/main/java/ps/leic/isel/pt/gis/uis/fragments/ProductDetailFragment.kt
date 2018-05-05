package ps.leic.isel.pt.gis.uis.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ProductDTO
import ps.leic.isel.pt.gis.utils.ExtraUtils

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProductDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProductDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProductDetailFragment : Fragment() {

    private lateinit var product: ProductDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getParcelable(ExtraUtils.PRODUCT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_product, container, false)

        //TODO

        return view
    }

    override fun onStart() {
        super.onStart()
        activity?.title = product.name
    }

    /**
     * ProductDetailFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param product Product
         * @return A new instance of fragment ProductDetailFragment.
         */
        @JvmStatic
        fun newInstance(product: ProductDTO) =
                ProductDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ExtraUtils.PRODUCT, product)
                    }
                }
    }
}
