package ps.leic.isel.pt.gis.stores

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class CredentialsStoreImpl(val applicationContext: Context) : CredentialsStore {

    override fun getUsername(): String? {
        val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        return mPreferences.getString(CredentialsStore.USERNAME, null)
    }

    override fun getCredentials(): CredentialsStore.Credentials? {
        val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val username: String? = mPreferences.getString(CredentialsStore.USERNAME, null)
        val password: String? = mPreferences.getString(CredentialsStore.PASSWORD, null)
        return if (username == null || password == null) null else CredentialsStore.Credentials(username, password)

    }

    override fun storeCredentials(credentials: CredentialsStore.Credentials): Boolean {
        val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        mPreferences
                .edit()
                .putString(CredentialsStore.USERNAME, credentials.username)
                .putString(CredentialsStore.PASSWORD, credentials.password)
                .apply()
        return true
    }

    override fun deleteCredentials(): Boolean {
        val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        mPreferences
                .edit()
                .remove(CredentialsStore.USERNAME)
                .remove(CredentialsStore.PASSWORD)
                .apply()
        return true
    }
}