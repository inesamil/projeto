package ps.leic.isel.pt.hardwaresimulator

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
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

        val houses = arrayOf("Santos", "Oliveira")
        val storages = arrayOf("Frigorífico")

        val housesSpinnerAdapter = ArrayAdapter<String>(housesSpinner.context, android.R.layout.simple_spinner_item, houses)
        housesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        housesSpinner.adapter = housesSpinnerAdapter
        housesSpinner.setSelection(0)

        val storagesSpinnerAdapter = ArrayAdapter<String>(storagesSpinner.context, android.R.layout.simple_spinner_item, storages)
        storagesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        storagesSpinner.adapter = storagesSpinnerAdapter
        storagesSpinner.setSelection(0)

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
            if (isDialogDisplayed) {
                readFragment = supportFragmentManager.findFragmentByTag(ReadFragment.TAG) as? ReadFragment
                readFragment?.onNfcDetected(intent)
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
