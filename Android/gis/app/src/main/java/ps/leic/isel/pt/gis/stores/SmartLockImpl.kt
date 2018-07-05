package ps.leic.isel.pt.gis.stores

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.util.Log
import com.google.android.gms.auth.api.credentials.*
import com.google.android.gms.common.api.ResolvableApiException
import java.lang.Exception

class SmartLockImpl(applicationContext: Context) : SmartLock {

    private val options: CredentialsOptions = CredentialsOptions.Builder()
            .forceEnableSaveDialog()
            .build()
    private val mCredentialsClient = Credentials.getClient(applicationContext, options) // Credentials

    override fun storeCredentials(activity: Activity, username: String, password: String, onSuccess: () -> Unit, onException: (Exception?) -> Unit) {
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
                val e = it.exception
                if (e is ResolvableApiException) {
                    // Try to resolve the save request. This will prompt the user if
                    // the credential is new.
                    try {
                        e.startResolutionForResult(activity, SmartLock.RC_SAVE)
                    } catch (e: IntentSender.SendIntentException) {
                        // Could not resolve the request
                        Log.e(TAG, "Failed to send resolution.", e)
                        onException(it.exception)
                    }
                } else {
                    // Request has no resolution
                    onException(it.exception)
                }
            }
        }
    }

    override fun retrieveCredentials(activity: Activity, onSuccess: (Credential) -> Unit, onUnsuccess: () -> Unit) {
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
            val e = it.exception
            if (e is ResolvableApiException) {
                // This is most likely the case where the user has multiple saved
                // credentials and needs to pick one. This requires showing UI to
                // resolve the read request.
                try {
                    e.startResolutionForResult(activity, SmartLock.RC_READ)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Failed to send resolution.", e)
                    onUnsuccess()
                }
            } else {
                // Unsuccessful and incomplete credential requests
                onUnsuccess()
            }
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

    override fun disableAutoSignIn() {
        mCredentialsClient.disableAutoSignIn()
    }

    companion object {
        private const val TAG: String = "SmartLock"
    }
}