package com.example.nunoveloso.myapplication

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.nunoveloso.myapplication.utils.NFCUtils

class NFCWriterActivity : AppCompatActivity() {

    private var nfcAdapter: NfcAdapter? = null
    private var isWriteReady: Boolean = false

    private lateinit var progressBar: ProgressBar
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfcwriter)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE

        saveBtn = findViewById(R.id.save_btn)
        saveBtn.setOnClickListener{
            saveBtn.isEnabled = false
            progressBar.visibility = View.VISIBLE

            nfcAdapter?.let {
                NFCUtils.enableNFCInForeground(it, this)
                return@setOnClickListener
            }
            Toast.makeText(this, "NFC is not available in this device.", Toast.LENGTH_LONG).show()
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
        intent?.let {
            val tag: Tag? = it.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            val message = createNFCMessage()
            val successfulWriting = NFCUtils.writeToNFC(tag, message)

            if (successfulWriting){
                nfcAdapter?.let {
                    NFCUtils.disableNFCInForeground(it, this)
                }
                progressBar.visibility = View.GONE
                saveBtn.isEnabled = true
            }
        }
    }

    fun createNFCMessage() : String {
        return ""
    }
}
