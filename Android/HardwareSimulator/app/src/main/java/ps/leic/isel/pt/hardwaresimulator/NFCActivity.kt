package ps.leic.isel.pt.hardwaresimulator

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_nfc.*
import ps.leic.isel.pt.hardwaresimulator.utils.NFCUtils

class NFCActivity : AppCompatActivity(), Listener {

    private var nfcAdapter: NfcAdapter? = null
    private var readFragment: ReadFragment? = null
    private var isDialogDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        readBtn.setOnClickListener {
            readFragment = supportFragmentManager.findFragmentByTag(ReadFragment.TAG) as? ReadFragment
            if (readFragment == null)
                readFragment = ReadFragment.newInstance()
            readFragment?.show(supportFragmentManager, ReadFragment.TAG)
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.let {
            NFCUtils.enableNFCInForeground(it, this)
        }
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.let {
            NFCUtils.disableNFCInForeground(it, this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val tag = intent?.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show()
            readFragment = supportFragmentManager.findFragmentByTag(ReadFragment.TAG) as? ReadFragment
            readFragment?.isVisible?.let {
                if (it) {
                    val type = switchBtn.isChecked
                    val storageId = storageIdEditText.text.toString().toShort()
                    val houseId = houseIdEditText.text.toString().toLong()
                    readFragment?.onNfcDetected(intent, storageId, type, houseId)
                }
            }
        }
    }

    override fun onDialogDisplayed() {
        isDialogDisplayed = true
    }

    override fun onDialogDismissed() {
        isDialogDisplayed = false
    }
}
