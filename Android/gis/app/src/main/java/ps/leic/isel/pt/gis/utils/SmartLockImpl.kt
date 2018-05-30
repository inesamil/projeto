package ps.leic.isel.pt.gis.utils

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.IdentityProviders
import java.lang.Exception

class SmartLockImpl(applicationContext: Context) : SmartLock {

    private val mCredentialsClient = Credentials.getClient(applicationContext) // Credentials

    override fun storeCredentials(username: String, password: String, onSuccess: () -> Unit, onException: (Exception?) -> Unit) {
        val credential = Credential.Builder(username)
                .setPassword(password)  // Important: only store passwords in this field.
                // Android autofill uses this value to complete
                // sign-in forms, so repurposing this field will
                // likely cause errors.
                .build()

        mCredentialsClient.save(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess()
            } else {
                onException(it.exception)
            }
        }
    }

    override fun retrieveCredentials(onSuccess: (Credential) -> Unit, onUnsuccess: () -> Unit) {
        val mCredentialRequest: CredentialRequest = CredentialRequest.Builder()
                .setPasswordLoginSupported(true)    // Password-based sign-in
                //.setAccountTypes(IdentityProviders.GOOGLE, IdentityProviders.TWITTER) // Federated sign-in services such as Google Sign-In
                .build()    // Specifies the sign-in systems from where credentials should be requested
        // Request stored credentials
        mCredentialsClient.request(mCredentialRequest).addOnCompleteListener {
            if (it.isSuccessful) {
                // Successful credential request
                it.result.credential.let {
                    onCredentialRetrieved(it, onSuccess)
                }
                return@addOnCompleteListener
            }
            // Unsuccessful and incomplete credential requests
            onUnsuccess()
        }
    }

    private fun onCredentialRetrieved(credential: Credential, onSuccess: (Credential) -> Unit) {
        val accountType = credential.accountType
        if (accountType == null) {
            // Sign the user in with information from the Credential.
            onSuccess(credential)
        } else if (accountType == IdentityProviders.GOOGLE) {
            // The user has previously signed in with Google Sign-In. Silently
            // sign in the user with the same ID.
            // See https://developers.google.com/identity/sign-in/android/
            /*val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()

            val signInClient = GoogleSignIn.getClient(this, gso)
            val task = signInClient.silentSignIn()*/
        }
    }


    override fun deleteCredentials(credential: Credential) {
        mCredentialsClient.delete(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "Credential successfully deleted.")
            } else {
                Log.d(TAG, "Credential not deleted successfully.")
            }
        }
    }

    companion object {
        private const val TAG: String = "SmartLock"
    }
}