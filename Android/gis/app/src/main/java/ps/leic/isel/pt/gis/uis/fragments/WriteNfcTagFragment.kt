package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_write_nfc_tag.*
import kotlinx.android.synthetic.main.fragment_write_nfc_tag.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.*
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.activities.HomeActivity
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
    private var previousSelectedCategoryPosition: Int = first

    private lateinit var url: String
    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var categoryProductsViewModel: CategoryProductsViewModel

    private var categories: Array<CategoryDto>? = null
    private var products: Array<ProductDto>? = null

    private var state: State = State.LOADING
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ConstraintLayout

    private lateinit var categorySpinner: Spinner
    private lateinit var productSpinner: Spinner
    private lateinit var segmentUnitSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL_KEY)
        }
        getCategories(url)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_write_nfc_tag, container, false)

        // Set write button listener
        view.writeBtn.setOnClickListener(::onWriteClick)

        //TODO: get units from API
        val units = arrayOf("kg", "dag", "hg", "g", "dg", "cg", "mg", "kl", "hl", "dal", "l", "dl", "cl", "ml", "oz", "lb", "pt", "fl oz", "units")

        categorySpinner = view.categorySpinner
        productSpinner = view.productSpinner
        segmentUnitSpinner = view.segmentUnitSpinner

        val spinnerAdapter = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, units)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        segmentUnitSpinner.adapter = spinnerAdapter

        progressBar = view.writeNfcTagProgressBar
        content = view.writeNfcTagLayout

        showProgressBarOrContent()

        // Set brand info listener
        val brandEditText = view.brandEditText
        brandEditText.setOnTouchListener { v, event ->
            return@setOnTouchListener onRigthDrawableTouchListener(v, event, brandEditText, getString(R.string.brand), getString(R.string.brand_info))
        }

        // Set variety info listener
        val varietyEditText = view.varietyEditText
        varietyEditText.setOnTouchListener { v, event ->
            return@setOnTouchListener onRigthDrawableTouchListener(v, event, varietyEditText, getString(R.string.variety), getString(R.string.variety_info))
        }

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

    /***
     * Auxiliary Methods
     ***/

    private fun onSuccess(categories: CategoriesDto?) {
        categories?.let {
            this.categories = it.categories

            this.categories?.let {
                val spinnerAdapter = ArrayAdapter<String>(categorySpinner.context, android.R.layout.simple_spinner_item, it.map { category -> category.categoryName })
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                categorySpinner.adapter = spinnerAdapter
                categorySpinner.onItemSelectedListener = this
                categorySpinner.setSelection(first)
            }

            val size = this.categories?.size ?: 0
            if (size > 0) {
                this.categories?.get(first)?.links?.productsCategoryLink?.let {
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

    private fun onSuccess(products: ProductsDto?) {
        products?.let {
            state = State.SUCCESS

            // Show progress bar or content
            showProgressBarOrContent()

            this.products = it.products

            this.products?.let {
                val spinnerAdapter = ArrayAdapter<String>(productSpinner.context, android.R.layout.simple_spinner_item, it.map { product -> product.productName })
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                productSpinner.adapter = spinnerAdapter
                productSpinner.setSelection(first)
            }
        }
    }

    private fun showProgressBarOrContent() {
        progressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
        content.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
    }

    private fun onRigthDrawableTouchListener(v: View, event: MotionEvent, editText: EditText, title: String, info: String): Boolean {
        //val DRAWABLE_LEFT = 0, DRAWABLE_TOP = 1, DRAWABLE_BOTTOM = 3
        val DRAWABLE_RIGHT = 2

        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(event.rawX >= (editText.right - editText.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                val infoDialogFragment = InfoDialogFragment.newInstance(title, info)
                infoDialogFragment.show(fragmentManager, InfoDialogFragment.TAG)
                return true;
            }
        }
        return false
    }

    /***
     * Listeners
     ***/

    fun onNfcDetected(intent: Intent?) {
        writingFragment?.let {
            if (it.isVisible)
                writingFragment?.onNfcDetected(intent)
        }
    }

    private fun onWriteClick(v: View?) {
        val data = "\"${productSpinner.selectedItem}\",\"${brandEditText.text}\",\"${varietyEditText.text}\",\"${segmentNumberEditText.text}${segmentUnitSpinner.selectedItem}\",${dateEditText.text}"
        writingFragment = fragmentManager?.findFragmentByTag(WritingNfcTagFragment.TAG) as? WritingNfcTagFragment
        if (writingFragment == null)
            writingFragment = WritingNfcTagFragment.newInstance(data)
        writingFragment?.show(fragmentManager, WritingNfcTagFragment.TAG)
    }

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

    /**
     * WriteNfcTagFragment Factory
     */
    companion object {
        val TAG: String = WriteNfcTagFragment::class.java.simpleName
        private const val URL_KEY: String = "URL"
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
                putString(URL_KEY, url)
            }
        }
    }
}
