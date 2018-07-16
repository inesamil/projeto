package ps.leic.isel.pt.gis.uis.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.body.UserBody
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
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
        val confirmationPassword = confirmPasswordEditText.text.toString()

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_fill_in_all_required_fields), Toast.LENGTH_LONG).show()
            return
        }
        if (password != confirmationPassword) {
            Toast.makeText(this, getString(R.string.passwords_must_be_equal), Toast.LENGTH_LONG).show()
            return
        }

        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        usersViewModel?.addUser(UserBody(username, name, email, age, password))?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> onSuccess(username, password)
                it?.status == Status.UNSUCCESS -> onUnsuccess(it.apiError)
                it?.status == Status.ERROR -> onError(it.message!!)
            }
        })
    }

    private fun onSuccess(username: String, password: String) {
        Log.i(TAG, "Register user with success")
        ServiceLocator.getCredentialsStore(applicationContext).storeCredentials(CredentialsStore.Credentials(username, password))
        Log.i(TAG, "Credentials stored. Login succeeded.")
        finish()
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun onUnsuccess(error: ErrorDto?) {
        error?.let {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            Log.e(TAG, it.developerErrorMessage)
        }
    }

    private fun onError(message: String?) {
        Toast.makeText(this, getString(R.string.could_not_connect_to_server), Toast.LENGTH_SHORT).show()
        message?.let {
            Log.e(TAG, it)
        }
    }

    companion object {
        private const val TAG: String = "RegisterActivity"
    }
}
