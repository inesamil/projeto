package ps.leic.isel.pt.hardwaresimulator.utils

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable

object NFCUtils {

    fun enableNFCInForeground(nfcAdapter: NfcAdapter, activity: Activity) {
        val pendingIntent = PendingIntent.getActivity(activity, 0,
                Intent(activity, activity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        val tagDetected = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        val ndefDetected = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val techDetected = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        val nfcIntentFilters = arrayOf(techDetected, tagDetected, ndefDetected)
        val techLists = arrayOf(arrayOf(Ndef::class.java.name),
                arrayOf(NdefFormatable::class.java.name))
        nfcAdapter.enableForegroundDispatch(activity, pendingIntent, nfcIntentFilters, techLists)
    }

    fun disableNFCInForeground(nfcAdapter: NfcAdapter, activity: Activity) {
        nfcAdapter.disableForegroundDispatch(activity)
    }

    fun readFromNFC(intent: Intent?): String? {
        val tag = intent?.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        val ndefTag = Ndef.get(tag)
        ndefTag?.use {
            it.connect()
            val ndefMessage: NdefMessage? = it.ndefMessage
            return ndefMessage?.records?.get(0)?.payload?.let { String(it) }
        }
        return null
    }
}