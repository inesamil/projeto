package ps.leic.isel.pt.gis.uis.fragments

import android.content.Context
import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_write_nfc_tag.*
import kotlinx.android.synthetic.main.fragment_write_nfc_tag.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.CategoryDTO
import ps.leic.isel.pt.gis.model.ProductDTO
import ps.leic.isel.pt.gis.utils.NFCUtils

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

    private val first: Int = 0

    private lateinit var categories: Array<CategoryDTO>
    private lateinit var products: Array<ProductDTO>

    private var listener: OnWriteNfcTagFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: get categories
        categories = arrayOf(
                CategoryDTO(1, "Laticínios"),
                CategoryDTO(2, "Carne"),
                CategoryDTO(3, "Peixe"))
        products = arrayOf(
                ProductDTO(1,  1, "Leite", true, "3dias"),
                ProductDTO(1, 2, "Queijo", true, "7dias"),
                ProductDTO(1, 3, "Iogurte", true, "20dias"))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_write_nfc_tag, container, false)

        // Set category spinner options
        val categorySpinnerAdapter = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, categories.map { category -> category.categoryName })
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.categorySpinner.adapter = categorySpinnerAdapter
        view.categorySpinner.onItemSelectedListener = this
        view.categorySpinner.setSelection(first)

        // Set write button listener
        view.writeBtn.setOnClickListener{
            onWriteClick(it)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnWriteNfcTagFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnWriteNfcTagFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Listeners
     ***/
    fun onWriteClick(v: View?) {
        //TODO: collect data
        val tagContent: String = conservationStorageText.text.toString()
        if (tagContent.isNotEmpty())
            listener?.onWriteNfcTagInteraction(tagContent)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        view?.let {
            when(view.id){
                R.id.categorySpinner -> {
                    // Set category spinner options
                    //categorySpinner.selectedItem
                    //TODO: get products
                    val productSpinnerAdapter = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, products.map { product -> product.name })
                    productSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    view.productSpinner.adapter = productSpinnerAdapter
                    view.productSpinner.setSelection(first)
                    productSpinnerAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Nothing to do here
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnWriteNfcTagFragmentInteractionListener {
        fun onWriteNfcTagInteraction(tagContent: String)
    }

    /**
     * WriteNfcTagFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment WriteNfcTagFragment.
         */
        @JvmStatic
        fun newInstance() = WriteNfcTagFragment()
    }
}
