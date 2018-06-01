package ps.leic.isel.pt.gis

import android.content.res.Configuration
import android.support.multidex.MultiDexApplication
import ps.leic.isel.pt.gis.model.dtos.IndexDto
import java.util.*

class GisApplication : MultiDexApplication () {

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
}