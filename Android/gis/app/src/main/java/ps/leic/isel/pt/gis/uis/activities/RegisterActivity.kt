package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.common.api.ResolvableApiException
import kotlinx.android.synthetic.main.activity_register.*
import ps.leic.isel.pt.gis.R
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {

    // These are the API calls that can be made.
    private val RC_SAVE = 1
    private val RC_READ = 3
    private val RC_DELETE = 4

    private val FIRST_TIME_TAG = "first_time"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signupBtn.setOnClickListener {
            val credentials: Credential = Credential.Builder(usernameEditText.text.toString())
                    .setPassword(passwordEditText.text.toString())
                    .build()
            // Registe user
            registeUser(
                    credentials,
                    fullnameEditText.text.toString(),
                    emailEditText.text.toString(),
                    Integer.parseInt(ageEditText.text.toString())
            )

            // Save credentials for future logins
            saveCredentials(credentials)

            // First run completed
            val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            mPreferences.getBoolean(FIRST_TIME_TAG, false)
            val mEditor = mPreferences.edit()
            mEditor.putBoolean(FIRST_TIME_TAG, false)
            mEditor.apply()

            // Setup
            goToInitialSetup()
        }

        signinBtn.setOnClickListener {
            gotToLogin()
        }
    }

    private fun saveCredentials(credentials: Credential) {
        val credentialsClient: CredentialsClient = Credentials.getClient(this)
        credentialsClient.save(credentials).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("", "SAVE: OK")
                return@addOnCompleteListener
            }
            val ex: Exception? = it.exception
            ex.let {
                if (ex is ResolvableApiException) {
                    // Try to resolve the save request. This will prompt the user if
                    // the credential is new.
                    val rae: ResolvableApiException = ex
                    try {
                        rae.startResolutionForResult(this, RC_SAVE)
                    } catch (e: IntentSender.SendIntentException) {
                        // Could not resolve the request
                        Log.e("", "Failed to send resolution.", e)
                    }
                } else {
                    // Request has no resolution
                    Log.d("", "SAVE: failed")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SAVE)
            Log.d("", "SAVE: OK")
        else
            Log.e("", "SAVE: Canceled by user")
    }

    private fun registeUser(credentials: Credential, name: String, email: String, age: Int) {
        //TODO: request api
    }

    private fun gotToLogin() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun goToInitialSetup() {
        finish()
        startActivity(Intent(this, InitialSetupActivity::class.java))
    }
}
