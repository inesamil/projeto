package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.utils.CredentialsStore

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signupBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        signinBtn.setOnClickListener(::onLoginClick)
    }

    fun onLoginClick(view: View) {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, getString(R.string.please_fill_in_all_required_fields), Toast.LENGTH_LONG).show()
            return
        }

        if (true) { //TODO: validate credentials
            val credentials: CredentialsStore.Credentials = CredentialsStore.Credentials(username, password)
            ServiceLocator.getCredentialsStore(applicationContext).storeCredentials(credentials)
            Log.i(TAG, "Credentials stored. Login succeeded.")
            finish()
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            Log.i(TAG, "Wrong credentials.")
            Toast.makeText(this, getString(R.string.wrong_credentials), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TAG: String = "LoginActivity"
    }
}
