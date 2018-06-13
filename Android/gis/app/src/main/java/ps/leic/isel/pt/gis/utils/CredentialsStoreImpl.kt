package ps.leic.isel.pt.gis.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class CredentialsStoreImpl(val applicationContext: Context) : CredentialsStore {

    override fun getUsername(): String? {
        val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        return mPreferences.getString(CredentialsStore.USERNAME, null)
    }

    override fun storeUsername(username: String) {
        val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        mPreferences.edit().putString(CredentialsStore.USERNAME, username).apply()
    }

    override fun deleteUsername() {
        val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        mPreferences.edit().remove(CredentialsStore.USERNAME).apply()
    }
}