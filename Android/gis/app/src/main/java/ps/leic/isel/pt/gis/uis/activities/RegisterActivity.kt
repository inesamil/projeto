package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_register.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.utils.SmartLock

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signupBtn.setOnClickListener {

        }

        signinBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SmartLock.RC_SAVE)
            Log.d(TAG, "SAVE: OK")
        else
            Log.e(TAG, "SAVE: Canceled by user")
    }

    private fun goToInitialSetup() {
        finish()
        startActivity(Intent(this, InitialSetupActivity::class.java))
    }

    companion object {
        private const val TAG: String = "RegisterActivity"
    }
}
