package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ProductDTO
import ps.leic.isel.pt.gis.model.dtos.ProductDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.viewModel.ProductDetailViewModel

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

    private lateinit var product: ProductDto
    private var productDetailViewModel: ProductDetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //TODO product = it.getParcelable(ExtraUtils.PRODUCT)
        }
        productDetailViewModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
        val url = ""
        productDetailViewModel?.init(url)
        productDetailViewModel?.getProduct()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
    }

    private fun onSuccess(product: ProductDto) {
        //TODO
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onStart() {
        super.onStart()
        activity?.title = product.productName
    }

    /**
     * ProductDetailFragment Factory
     */
    companion object {
        val productArg: String = "product"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param product Product
         * @return A new instance of fragment ProductDetailFragment.
         */
        @JvmStatic
        fun newInstance(args: Map<String, Any>) =
                ProductDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ExtraUtils.PRODUCT, args[productArg] as ProductDTO)
                    }
                }
    }
}
