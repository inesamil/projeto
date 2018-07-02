package ps.leic.isel.pt.gis.uis.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import ps.leic.isel.pt.gis.GisApplication
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.stores.CredentialsStore
import ps.leic.isel.pt.gis.viewModel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private var url: String? = null
    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signupBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        signinBtn.setOnClickListener(::onLoginClick)
    }

    private fun onLoginClick(view: View) {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_fill_in_all_required_fields), Toast.LENGTH_LONG).show()
            return
        }

        ServiceLocator.getCredentialsStore(applicationContext).storeCredentials(CredentialsStore.Credentials(username, password))
        Log.i(TAG, "Credentials stored.")

        val gisApplication = application as GisApplication
        gisApplication.index

        url = gisApplication.index.getUserUrl(username)

        url?.let {
            userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
            userViewModel?.init(it)
            userViewModel?.getUser()?.observe(this, Observer {
                when (it?.status) {
                    Status.SUCCESS -> onSuccess(it.data)
                    Status.UNSUCCESS -> onUnsuccess(it.apiError)
                    Status.ERROR -> onError(it.message)
                }
            })
        }
    }

    private fun onSuccess(user: UserDto?) {
        Log.i(TAG, "Login succeeded.")
        finish()
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun onUnsuccess(error: ErrorDto?) {
        error?.let {
            Log.e(TAG, it.developerErrorMessage)
            if (it.statusCode == 401) {
                Log.i(TAG, "Wrong credentials.")
                Toast.makeText(this, getString(R.string.wrong_credentials), Toast.LENGTH_SHORT).show()
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                ServiceLocator.getCredentialsStore(applicationContext).deleteCredentials()
                Log.i(TAG, "Credentials deleted.")
            }
        }
    }

    private fun onError(message: String?) {
        message?.let {
            Log.e(TAG, it)
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val TAG: String = "LoginActivity"

    }
}
