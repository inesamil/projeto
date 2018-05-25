package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.credentials.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.utils.CredentialsUtils


class MainActivity : AppCompatActivity() {

    private var firstTime: Boolean? = null
    private val FIRST_TIME_TAG = "first_time"

    private lateinit var mCredentialsClient: CredentialsClient
    private val SMART_LOCK_TAG = "SMART LOCK"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isFirstTime())
            goToRegister()
        else {
            mCredentialsClient = Credentials.getClient(this) // Credentials
            val mCredentialRequest: CredentialRequest = CredentialRequest.Builder()
                    .setPasswordLoginSupported(true)    // Password-based sign-in
                    //.setAccountTypes(IdentityProviders.GOOGLE, IdentityProviders.TWITTER) // Federated sign-in services such as Google Sign-In
                    .build()    // Specifies the sign-in systems from where credentials should be requested

            // Request stored credentials
            mCredentialsClient.request(mCredentialRequest).addOnCompleteListener {
                if (it.isSuccessful) {
                    // Successful credential request
                    it.result.credential.let {
                        onCredentialRetrieved(it)
                    }
                    return@addOnCompleteListener
                }
                // Unsuccessful and incomplete credential requests
                goToLogin()
            }
        }
    }

    private fun isFirstTime(): Boolean {
        if (firstTime == null) {
            val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            firstTime = mPreferences.getBoolean(FIRST_TIME_TAG, true)
        }
        return firstTime as Boolean
    }

    private fun onCredentialRetrieved(credential: Credential) {
        val accountType = credential.accountType
        if (accountType == null) {
            // Sign the user in with information from the Credential.
            signInWithPassword(credential)
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

    private fun signInWithPassword(credential: Credential) {
        /*val credential: Credential = Credential.Builder(c)
                .setPassword(password)  // Important: only store passwords in this field.
                                        // Android autofill uses this value to complete
                                        // sign-in forms, so repurposing this field will
                                        // likely cause errors.
                .build()*/
        if (CredentialsUtils.isValidCredential(credential)) {
            goToContent()
        } else {
            Log.d(SMART_LOCK_TAG, "Retrieved invalid credential, so delete retrieved credential.")
            Toast.makeText(this, "Retrieved credentials are invalid.", Toast.LENGTH_SHORT).show()
            deleteCredential(credential)
            goToLogin()
        }
    }

    private fun deleteCredential(credential: Credential) {
        mCredentialsClient.delete(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(SMART_LOCK_TAG, "Credential successfully deleted.");
            } else {
                Log.d(SMART_LOCK_TAG, "Credential not deleted successfully.");
            }
        }
    }

    private fun goToRegister() {
        finish()
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun goToLogin() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun goToContent() {
        finish()
        startActivity(Intent(this, HomeActivity::class.java))
    }
}
