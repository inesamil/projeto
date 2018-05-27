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
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.utils.CredentialsUtils


class MainActivity : AppCompatActivity() {

    private var firstTime: Boolean? = null
    private val FIRST_TIME_TAG = "first_time"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isFirstTime()){
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        } else {
            ServiceLocator.getSmartLock(applicationContext).retrieveCredentials({
                // Credentials retrieved
                if (true) {    //TODO: validateCredentials
                    // Valid Credentials
                    finish()
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    // Invalid Credentials
                    ServiceLocator.getSmartLock(applicationContext).deleteCredentials(it)
                    Log.d(TAG, "Retrieved invalid credential, so delete retrieved credential.")
                    Toast.makeText(this, "Retrieved credentials are invalid.", Toast.LENGTH_SHORT).show()
                }
            }, {
                // No credentials retrieved
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            })
        }
    }

    private fun isFirstTime(): Boolean {
        if (firstTime == null) {
            val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            firstTime = mPreferences.getBoolean(FIRST_TIME_TAG, true)
        }
        return firstTime as Boolean
    }

    companion object {
        private const val TAG: String = "MainActivity"
    }
}
