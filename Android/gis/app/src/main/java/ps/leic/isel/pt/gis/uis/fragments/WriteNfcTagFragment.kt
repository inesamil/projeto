package ps.leic.isel.pt.gis.uis.fragments

import android.content.Context
import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_write_nfc_tag.*
import kotlinx.android.synthetic.main.fragment_write_nfc_tag.view.*
import ps.leic.isel.pt.gis.R
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
class WriteNfcTagFragment : Fragment(), View.OnClickListener {

    private var nfcAdapter: NfcAdapter? = null
    private var isDialogDisplayed = false

    private var listener: OnWriteNfcTagFragmentInteractionListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_write_nfc_tag, container, false)

        // Set write button listener
        view.writeBtn.setOnClickListener(this)

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
    override fun onClick(v: View?) {
        //TODO: collect data
        val tagContent: String = msgTxt.text.toString()
        if (tagContent.isNotEmpty())
            listener?.onWriteNfcTagInteraction(tagContent)
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
