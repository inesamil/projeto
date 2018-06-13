package ps.leic.isel.pt.gis.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class CookieStoreImpl(val applicationContext: Context) : CookieStore {

    override fun getSessionCookie(): String? {
        val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        return mPreferences.getString(CookieStore.SESSION_COOKIE, null)
    }

    override fun storeSessionCookie(sessionCookie: String) {
        val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        mPreferences.edit().putString(CookieStore.SESSION_COOKIE, sessionCookie).apply()
    }

    override fun deleteSessionCookie() {
        val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        mPreferences.edit().remove(CookieStore.SESSION_COOKIE).apply()
    }
}