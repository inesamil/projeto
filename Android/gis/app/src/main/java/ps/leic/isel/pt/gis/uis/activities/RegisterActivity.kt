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
import ps.leic.isel.pt.gis.stores.SmartLock
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SmartLock.RC_SAVE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "SAVE: OK")
                onComplete()
            } else {
                Log.e(TAG, "SAVE: Canceled by user")
                Toast.makeText(this, "Precisa aceitar o smart lock guardar a password", Toast.LENGTH_SHORT).show() // TODO msg nas strings
            }
        }
    }

    private fun onSignup(view: View) {
        val name = fullnameEditText.text.toString()
        val username = usernameEditText.text.toString()
        val email = emailEditText.text.toString()
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

        ServiceLocator
                .getSmartLock(applicationContext)
                .storeCredentials(this,
                        username,
                        password,
                        ::onComplete,
                        ::onUncomplete
                )
    }

    private fun onComplete() {
        val name = fullnameEditText.text.toString()
        val username = usernameEditText.text.toString()
        val email = emailEditText.text.toString()
        val age = ageEditText.text.toString().toShort()
        val password = passwordEditText.text.toString()

        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        usersViewModel?.addUser(UserBody(username, name, email, age, password))?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> onSuccess()
                it?.status == Status.UNSUCCESS -> onUnsuccess(it.apiError)
                it?.status == Status.ERROR -> onError(it.message)
            }
        })
    }

    private fun onUncomplete(exception: Exception?) {
        onError(getString(R.string.something_went_wrong_please_try_again)) // TODO mudar para a mensagem: tem de ativar o smart lock for passwords nas definiçoes do tlmv
    }

    private fun onSuccess() {
        Log.i(TAG, "Register user with success")
        finish()
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun onUnsuccess(error: ErrorDto?) {
        error?.let {
            Log.e(TAG, it.developerErrorMessage)
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }
        onError(null)
    }

    private fun onError(message: String?) {
        message?.let {
            Log.e(LoginActivity.TAG, it)
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        ServiceLocator
                .getSmartLock(applicationContext)
                .retrieveCredentials(this, {
                    ServiceLocator
                            .getSmartLock(applicationContext)
                            .deleteCredentials(it)
                }, {})
    }

    companion object {
        private const val TAG: String = "RegisterActivity"
    }
}
