package ps.leic.isel.pt.gis.uis.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_writing_nfc_tag.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.NFCUtils

class WritingNfcTagFragment : DialogFragment() {

    private lateinit var messageToWrite: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            messageToWrite = it.getString(ExtraUtils.NFC_MESSAGE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_writing_nfc_tag, container, false)

    // When NFC tag is detected call this method
    fun onNfcDetected(intent: Intent?) {
        progress.visibility = View.VISIBLE
        tv_message.text = getString(R.string.message_write_progress)
        val messageWrittenSuccessfully = NFCUtils.createNFCMessage(messageToWrite, intent)
        if (messageWrittenSuccessfully)
            tv_message.text = getString(R.string.message_write_success)
        else
            tv_message.text = getString(R.string.message_write_error)
        progress.visibility = View.GONE
    }

    /**
     * WritingNfcTagFragment Factory
     */
    companion object {
        val TAG: String = WritingNfcTagFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(message: String) =
                WritingNfcTagFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExtraUtils.NFC_MESSAGE, message)
                    }
                }
    }
}
