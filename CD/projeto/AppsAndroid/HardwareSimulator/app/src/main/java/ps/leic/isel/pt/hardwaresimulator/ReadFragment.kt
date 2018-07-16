package ps.leic.isel.pt.hardwaresimulator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.damnhandy.uri.template.UriTemplate
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

    fun onNfcDetected(intent: Intent?, storageId: Short, type: Boolean, houseId: Long) {
        NFCUtils.readFromNFC(intent)
        tv_message.text = getString(R.string.message_read_progress)
        val message = NFCUtils.readFromNFC(intent)
        if (message == null) {
            tv_message.text = getString(R.string.message_read_error)
            return
        }
        val movementDto = MovementDto(
                message,
                storageId,
                type,
                1
        )
        val url = UriTemplate.expand(URL_TEMPLATE, mapOf(Pair(HOUSE_ID, houseId)))
        val postRequest = PostRequest(Request.Method.POST, url, movementDto, ::onSuccess, ::onError)
        postRequest.tag = TAG
        Volley.newRequestQueue(activity?.applicationContext).add(postRequest)
        return
    }

    private fun onSuccess(statusCode: String) {
        tv_message.text = "O movimento foi registado com sucesso!"
        Log.i("APP_TEST", statusCode)
    }

    private fun onError(error: VolleyError?) {
        tv_message.text = "Pedimos desculpa, mas não é possível registar esse movimento..."
        error?.let {
            it.message?.let {
                Log.w("APP_TEST", it)
            }
        }
    }

    companion object {
        private const val HOUSE_ID = "house_id"
        private const val URL_TEMPLATE = "http://192.168.43.182:8081/v1/houses/{$HOUSE_ID}/movements" // TODO Change URL here
        val TAG: String = ReadFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = ReadFragment()
    }
}
