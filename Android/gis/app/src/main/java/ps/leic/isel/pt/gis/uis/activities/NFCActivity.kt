package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_nfc.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.uis.fragments.WritingNfcTagFragment
import ps.leic.isel.pt.gis.utils.NFCUtils
/*
class NFCActivity : AppCompatActivity(), WritingNfcTagFragment.NfcListener {

    private var nfcAdapter: NfcAdapter? = null
    private var writeFragment: WritingNfcTagFragment? = null
    private var isDialogDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_nfc)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        writeBtn.setOnClickListener {
            writeFragment = supportFragmentManager.findFragmentByTag(WritingNfcTagFragment.TAG) as? WritingNfcTagFragment
            if (writeFragment == null)
                writeFragment = WritingNfcTagFragment.newInstance()
            writeFragment?.show(supportFragmentManager, WritingNfcTagFragment.TAG)
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

    override fun onDialogDisplayed() {
        isDialogDisplayed = true
    }

    override fun onDialogDismissed() {
        isDialogDisplayed = false
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val tag = intent?.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show()
            if (isDialogDisplayed) {
                val messageToWrite = msgTxt.text.toString()
                writeFragment = supportFragmentManager.findFragmentByTag(WritingNfcTagFragment.TAG) as? WritingNfcTagFragment
                writeFragment?.onNfcDetected(messageToWrite, intent)
            }
        }
    }
}
*/