package ps.leic.isel.pt.gis.uis.activities

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.ResolvableApiException
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.utils.SmartLock

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signupBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        signinBtn.setOnClickListener {
            Log.i(TAG, usernameEditText.text.toString())
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (true) { //TODO: validate credentials
                ServiceLocator.getSmartLock(applicationContext).storeCredentials(username, password,
                        {
                            // On Success
                            Log.d(TAG, "SAVE: OK");
                            Toast.makeText(this, "Credentials saved", Toast.LENGTH_SHORT).show();
                        },
                        {
                            // On Exception
                            if (it is ResolvableApiException) {
                                // Try to resolve the save request. This will prompt the user if
                                // the credential is new.
                                val rae = it as ResolvableApiException?
                                try {
                                    rae!!.startResolutionForResult(this, SmartLock.RC_SAVE)
                                } catch (e: IntentSender.SendIntentException) {
                                    // Could not resolve the request
                                    Log.e(TAG, "Failed to send resolution.", e)
                                    Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                // Request has no resolution
                                Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show()
                            }
                        })
                startActivity(Intent(this, HomeActivity::class.java))
            }
            else {
                Log.i(TAG, "Wrong credentials")
                Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SmartLock.RC_SAVE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "SAVE: OK")
                Toast.makeText(this, "Credentials saved", Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "SAVE: Canceled by user")
            }
        }
    }

    companion object {
        private const val TAG: String = "LoginActivity"
    }
}
