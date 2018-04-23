package ps.leic.isel.pt.hardwaresimulator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_read.*
import ps.leic.isel.pt.hardwaresimulator.utils.NFCUtils

class ReadFragment : DialogFragment() {

    private var listener: Listener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NFCActivity
        listener?.onDialogDisplayed()
    }

    override fun onDetach() {
        super.onDetach()
        listener?.onDialogDismissed()
    }

    fun onNfcDetected(intent: Intent?) {
        NFCUtils.readFromNFC(intent)
        tv_message.text = getString(R.string.message_read_progress)
        val message = NFCUtils.readFromNFC(intent)
        if (message == null)
            tv_message.text = getString(R.string.message_read_error)
        else
            tv_message.text = message
    }

    companion object {
        val TAG = ReadFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = ReadFragment()
    }
}
