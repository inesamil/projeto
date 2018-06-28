package ps.leic.isel.pt.gis.stores

import com.google.android.gms.auth.api.credentials.Credential
import java.lang.Exception

interface SmartLock {

    fun storeCredentials(username: String, password: String, onSuccess: () -> Unit, onException: (Exception?) -> Unit)

    fun retrieveCredentials(onSuccess: (Credential) -> Unit, onUnsuccess: () -> Unit)

    fun deleteCredentials(credential: Credential)

    companion object {
        // These are the API calls that can be made.
        const val RC_SAVE = 1
        const val RC_READ = 3
        const val RC_DELETE = 4
    }
}