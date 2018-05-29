package ps.leic.isel.pt.gis

import android.app.Application
import android.content.res.Configuration
import ps.leic.isel.pt.gis.model.dtos.IndexDto
import java.util.*

class GisApplication : Application() {

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