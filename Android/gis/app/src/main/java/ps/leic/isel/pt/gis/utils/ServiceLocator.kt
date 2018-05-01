package ps.leic.isel.pt.gis.utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * Singleton to store information needed by the whole app
 */
@SuppressLint("StaticFieldLeak")
object ServiceLocator {
    private lateinit var appContext: Context
    private lateinit var language: String

    fun init(context: Context, locale: String){
        this.appContext = context
        this.language = locale
    }

    fun getContext() = this.appContext

    fun getLanguage() = this.language
}