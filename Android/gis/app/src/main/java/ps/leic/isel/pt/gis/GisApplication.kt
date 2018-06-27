package ps.leic.isel.pt.gis

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.support.multidex.MultiDexApplication
import ps.leic.isel.pt.gis.model.dtos.IndexDto
import java.util.*

class GisApplication : MultiDexApplication() {

    @Volatile
    lateinit var language: String

    @Volatile
    lateinit var index: IndexDto

    override fun onCreate() {
        super.onCreate()
        language = Locale.getDefault().language
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        language = Locale.getDefault().language
    }

    /**
     * Checks if the device has internet connection.
     *
     * @return true if the phone is connected to the internet, otherwise false
     */
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}