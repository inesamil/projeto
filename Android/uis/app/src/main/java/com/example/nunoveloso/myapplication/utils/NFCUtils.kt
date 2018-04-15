package com.example.nunoveloso.myapplication.utils

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import java.io.IOException
import java.nio.charset.Charset

object NFCUtils {

     fun enableNFCInForeground(nfcAdapter: NfcAdapter, activity: Activity){
        val pendingIntent = PendingIntent.getActivity(activity, 0,
                Intent(activity, activity.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)

        val nfcIntentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val filters = arrayOf(nfcIntentFilter)

        val techLists = arrayOf(arrayOf(Ndef::class.java.name),
                arrayOf(NdefFormatable::class.java.name))
        nfcAdapter.enableForegroundDispatch(activity, pendingIntent, filters, techLists)
    }

    fun disableNFCInForeground(nfcAdapter: NfcAdapter, activity: Activity){
        nfcAdapter.disableForegroundDispatch(activity)
    }

    fun writeToNFC(tag: Tag?, nfcMessage: String) : Boolean {
        try {
            val payload = nfcMessage.toByteArray(Charset.forName("US-ASCII"))
            val ndefRecord = NdefRecord(NdefRecord.TNF_MIME_MEDIA, NdefRecord.RTD_TEXT, null, payload)
            val ndefMessage = NdefMessage(arrayOf(ndefRecord))

            val ndefTag = Ndef.get(tag)
            ndefTag?.let {
                it.connect()
                if (it.maxSize < payload.size) {
                    //Message to large to write to NFC tag
                    return false
                }
                if (it.isWritable){
                    it.writeNdefMessage(ndefMessage)
                    it.close()
                    //Message was written to tag
                    return true
                } else{
                    //NFC tag is read-only
                    return false
                }
            }

            val ndefFormatableTag = NdefFormatable.get(tag)
            ndefFormatableTag?.let {
                try {
                    it.connect()
                    it.format(ndefMessage)
                    it.close()
                    //Message was written to tag
                    return true
                } catch (e: IOException){
                    //fail to format tag
                    return false
                }
            }
            //NDEF is not supported
            return false
        } catch (e: Exception){
            //Write operation was failed
        }
        return false
    }
}