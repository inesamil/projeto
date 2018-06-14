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
import ps.leic.isel.pt.gis.GisApplication
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.body.UserBody
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.utils.SmartLock
import ps.leic.isel.pt.gis.viewModel.BasicInformationViewModel

class RegisterActivity : AppCompatActivity() {

    private var url: String? = null
    private var basicInformationViewModel: BasicInformationViewModel? = null

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

        basicInformationViewModel = ViewModelProviders.of(this).get(BasicInformationViewModel::class.java)
        basicInformationViewModel?.addUser(UserBody(username, name, email, age, password))?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> onSuccess(it.data!!)
                it?.status == Status.ERROR -> onError(it.message!!)
            }
        })
    }

    private fun onSuccess(user: UserDto) {
        Log.i(TAG, "Registe user with success")
        user.username?.let {
            ServiceLocator.getCredentialsStore(applicationContext).storeUsername(user.username)
            Log.i(TAG, "Credentials stored. Login succeeded.")
            finish()
            startActivity(Intent(this, InitialSetupActivity::class.java))
        }
    }

    private fun onError(message: String) {
        Log.i(LoginActivity.TAG, "Unable to registe user")
        Toast.makeText(this, getString(R.string.registe_user_failed), Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG: String = "RegisterActivity"
    }
}
