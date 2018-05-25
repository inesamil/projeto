package ps.leic.isel.pt.gis

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import ps.leic.isel.pt.gis.httpRequest.HttpWebService
import ps.leic.isel.pt.gis.httpRequest.HttpWebServiceImpl

object ServiceLocator {

    private var httpWebService: HttpWebService? = null
    private const val CREDENTIALS_TAG = "credentials"

    fun getWebService(context: Context): HttpWebService? {
        if (httpWebService == null)
            httpWebService = HttpWebServiceImpl(context)
        return httpWebService
    }

    fun getUserCredentials(context: Context): String {
        val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return mPreferences.getString(CREDENTIALS_TAG, "")
    }
}