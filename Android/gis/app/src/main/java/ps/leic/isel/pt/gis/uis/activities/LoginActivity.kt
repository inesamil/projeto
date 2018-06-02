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
            Toast.makeText(this, "Please fill in the required fields", Toast.LENGTH_LONG).show()    //TODO: msg nas strings
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
            Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show()    //TODO: msg nas strings
        }
    }

    companion object {
        private const val TAG: String = "LoginActivity"
    }
}
