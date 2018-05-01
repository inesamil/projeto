package ps.leic.isel.pt.gis.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_write.*
import ps.leic.isel.pt.gis.Listener
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.activities.NFCActivity
import ps.leic.isel.pt.gis.utils.NFCUtils

class WriteFragment : DialogFragment() {

    private var listener: Listener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_write, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NFCActivity
        listener?.onDialogDisplayed()
    }

    override fun onDetach() {
        super.onDetach()
        listener?.onDialogDismissed()
    }

    fun onNfcDetected(messageToWrite: String, intent: Intent?) {
        progress.visibility = View.VISIBLE
        tv_message.text = getString(R.string.message_write_progress)
        val messageWrittenSuccessfully = NFCUtils.createNFCMessage(messageToWrite, intent)
        if (messageWrittenSuccessfully)
            tv_message.text = getString(R.string.message_write_success)
        else
            tv_message.text = getString(R.string.message_write_error)
        progress.visibility = View.GONE
    }

    companion object {
        val TAG = WriteFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = WriteFragment()
    }
}
