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

    private var productName: String? = null

    private lateinit var url: String
    private lateinit var productDetailViewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ExtraUtils.URL)
            productName = it.getString(ExtraUtils.PRODUCT_NAME)
        }
        productDetailViewModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
        productDetailViewModel.init(url)
        productDetailViewModel.getProduct()?.observe(this, Observer {
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
        //TODO
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // TODO se precisar de adapter, inicia-lo aqui
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(ExtraUtils.URL)
            productName = it.getString(ExtraUtils.PRODUCT_NAME)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = productName
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ExtraUtils.URL, url)
        outState.putString(ExtraUtils.PRODUCT_NAME, productName)
    }

    override fun onStop() {
        super.onStop()
        productDetailViewModel.cancel()
    }

    /**
     * ProductDetailFragment Factory
     */
    companion object {
        const val URL_ARG = "url"
        const val PRODUCT_NAME_ARG = "product-name"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param args Arguments
         * @return A new instance of fragment ProductDetailFragment.
         */
        @JvmStatic
        fun newInstance(args: Map<String, Any>) =
                ProductDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExtraUtils.URL, args[URL_ARG] as String)
                        putString(ExtraUtils.PRODUCT_NAME, args[PRODUCT_NAME_ARG] as String)
                    }
                }
    }
}
