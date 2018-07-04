package ps.leic.isel.pt.gis.stores

import android.app.Activity
import com.google.android.gms.auth.api.credentials.Credential
import java.lang.Exception

interface SmartLock {

    fun storeCredentials(activity: Activity, username: String, password: String, onSuccess: (Credential) -> Unit, onException: (Exception?) -> Unit)

    fun retrieveCredentials(activity: Activity, onSuccess: (Credential) -> Unit, onUnsuccess: () -> Unit)

    fun deleteCredentials(credential: Credential)

    fun disableAutoSignIn()

    companion object {
        // These are the API calls that can be made.
        const val RC_SAVE = 1
        const val RC_READ = 3
        const val RC_DELETE = 4
    }
}