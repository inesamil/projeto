package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_write_nfc_tag.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.CategoriesDto
import ps.leic.isel.pt.gis.model.dtos.CategoryDto
import ps.leic.isel.pt.gis.model.dtos.ProductDto
import ps.leic.isel.pt.gis.model.dtos.ProductsDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.NFCUtils
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.viewModel.CategoriesViewModel
import ps.leic.isel.pt.gis.viewModel.CategoryProductsViewModel

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [WriteNfcTagFragment.OnWriteNfcTagFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [WriteNfcTagFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class WriteNfcTagFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var nfcAdapter: NfcAdapter? = null
    private var writingFragment: WritingNfcTagFragment? = null

    private val first: Int = 0

    private var state: State = State.LOADING;
    private lateinit var url: String

    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var categoryProductsViewModel: CategoryProductsViewModel

    private var categories: Array<CategoryDto>? = null
    private var products: Array<ProductDto>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ExtraUtils.URL)
        }
        getCategories(url)
    }

    private fun getCategories(url: String) {
        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        categoriesViewModel.init(url)
        categoriesViewModel.getCategories()?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> onSuccess(it.data!!)
                it?.status == Status.ERROR -> onError(it.message)
                it?.status == Status.LOADING -> {
                    state = State.LOADING
                }
            }
        })

    }

    private fun onSuccess(categoriesDto: CategoriesDto) {
        state = State.LOADING

        // Show progress bar or content
        showProgressBarOrContent()

        categories = categoriesDto.categories

        categories?.let {
            val spinnerAdapter = ArrayAdapter<String>(view?.context, android.R.layout.simple_spinner_item, it.map { category -> category.categoryName })
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view?.categorySpinner?.adapter = spinnerAdapter
            view?.categorySpinner?.onItemSelectedListener = this
            view?.categorySpinner?.setSelection(first)
        }

        val size = categories?.size ?: 0
        if (size > 0) {
            categories?.get(first)?.links?.productsCategoryLink?.let {
                getProductsCategory(it)
            }
        }
    }

    private fun getProductsCategory(url: String) {
        categoryProductsViewModel = ViewModelProviders.of(this).get(CategoryProductsViewModel::class.java)
        categoryProductsViewModel.init(url)
        categoryProductsViewModel.getProducts()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
    }

    private fun showProgressBarOrContent() {
        view?.let {
            it.writeNfcTagProgressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
            it.writeNfcTagLayout.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun onError(error: String?) {
        state = State.ERROR
        error?.let {
            Log.v("APP_GIS", it)
        }
    }

    private fun onSuccess(productsDto: ProductsDto) {
        state = State.SUCCESS

        // Show progress bar or content
        showProgressBarOrContent()

        products = productsDto.products

        products?.let {
            val spinnerAdapter = ArrayAdapter<String>(view?.context, android.R.layout.simple_spinner_item, it.map { product -> product.productName })
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view?.productSpinner?.adapter = spinnerAdapter
            view?.productSpinner?.onItemSelectedListener = this
            view?.productSpinner?.setSelection(first)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_write_nfc_tag, container, false)

        // Set write button listener
        view.writeBtn.setOnClickListener(::onWriteClick)

        return view
    }

    override fun onStart() {
        super.onStart()
        // Set title
        activity?.title = getString(R.string.message_write_tag)
        // Set NFC adapter
        context.let {
            nfcAdapter = NfcAdapter.getDefaultAdapter(it)
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.let {
            activity?.let { it1 -> NFCUtils.enableNFCInForeground(it, it1) }
        }
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.let {
            activity?.let { it1 -> NFCUtils.disableNFCInForeground(it, it1) }
        }
    }

    fun onNfcDetected(intent: Intent?) {
        writingFragment?.let {
            if (it.isVisible)
                writingFragment?.onNfcDetected(intent)
        }
    }

    /***
     * Listeners
     ***/
    private fun onWriteClick(v: View?) {
        //TODO: collect data
        /* val tagContent: String = conservationStorageText.text.toString()
        if (tagContent.isNotEmpty())
            listener?.onWriteNfcTagInteraction(tagContent) */
        writingFragment = fragmentManager?.findFragmentByTag(WritingNfcTagFragment.TAG) as? WritingNfcTagFragment
        if (writingFragment == null)
            writingFragment = WritingNfcTagFragment.newInstance("ola"); // TODO passar o que for para escrever aqui
        writingFragment?.show(fragmentManager, WritingNfcTagFragment.TAG)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.let {
            if (it.categorySpinner.selectedItem != position) {
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

    /**
     * WriteNfcTagFragment Factory
     */
    companion object {
        val TAG: String = WriteNfcTagFragment::class.java.simpleName

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param url URL to categories resource
         * @return A new instance of fragment WriteNfcTagFragment.
         */
        @JvmStatic
        fun newInstance(url: String) = WriteNfcTagFragment().apply {
            arguments = Bundle().apply {
                putString(ExtraUtils.URL, url)
            }
        }
    }
}
