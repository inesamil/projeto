package ps.leic.isel.pt.gis.utils

import com.google.android.gms.auth.api.credentials.Credential
import java.lang.Exception

interface SmartLock {

    fun storeCredentials(username: String, password: String, onSuccess: () -> Unit, onException: (Exception?) -> Unit)

    fun retrieveCredentials(onSuccess: (Credential) -> Unit, onUnsuccess: () -> Unit)

    fun deleteCredentials(credential: Credential)

    companion object {
        // These are the API calls that can be made.
        val RC_SAVE = 1
        val RC_READ = 3
        val RC_DELETE = 4
    }
}