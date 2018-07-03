package ps.leic.isel.pt.gis.uis.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.R.id.fullnameEditText
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.body.UserBody
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.stores.CredentialsStore
import ps.leic.isel.pt.gis.viewModel.UsersViewModel

class RegisterActivity : AppCompatActivity() {

    private var usersViewModel: UsersViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signupBtn.setOnClickListener(::onSignup)

        signinBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun onSignup(view: View) {
        val name = fullnameEditText.text.toString()
        val username = usernameEditText.text.toString()
        val email = emailEditText.text.toString()
        val age = ageEditText.text.toString().toShort()
        val password = passwordEditText.text.toString()

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_fill_in_all_required_fields), Toast.LENGTH_LONG).show()
            return
        }

        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        usersViewModel?.addUser(UserBody(username, name, email, age, password))?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> onSuccess(it.data, password)
                it?.status == Status.UNSUCCESS -> onUnsuccess(it.apiError)
                it?.status == Status.ERROR -> onError(it.message!!)
            }
        })
    }

    private fun onSuccess(user: UserDto?, password: String) {
        Log.i(TAG, "Registe user with success")
        user?.let {
            ServiceLocator.getCredentialsStore(applicationContext).storeCredentials(CredentialsStore.Credentials(user.username, password))
            Log.i(TAG, "Credentials stored. Login succeeded.")
            finish()
            startActivity(Intent(this, HomeActivity::class.java))
        }
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
            Log.e(LoginActivity.TAG, it)
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TAG: String = "RegisterActivity"
    }
}
