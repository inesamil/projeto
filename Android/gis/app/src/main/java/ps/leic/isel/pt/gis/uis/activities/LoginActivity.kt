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
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.utils.CredentialsStore
import ps.leic.isel.pt.gis.viewModel.BasicInformationViewModel

class LoginActivity : AppCompatActivity() {

    private var url: String? = null
    private var basicInformationViewModel: BasicInformationViewModel? = null

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
            basicInformationViewModel = ViewModelProviders.of(this).get(BasicInformationViewModel::class.java)
            basicInformationViewModel?.init(it)
            basicInformationViewModel?.getUser()?.observe(this, Observer {
                when {
                    it?.status == Status.SUCCESS -> onSuccess(it.data)
                    it?.status == Status.ERROR -> onError(it.message)
                }
            })
        }
    }

    private fun onSuccess(user: UserDto?) {
        Log.i(TAG, "Login succeeded.")
        finish()
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun onError(message: String?) {
        //TODO: pode não ter sido wrong credentials é preciso verificar
        ServiceLocator.getCredentialsStore(applicationContext).deleteCredentials()
        Log.i(TAG, "Credentials deleted.")

        Log.i(TAG, "Wrong credentials.")
        Toast.makeText(this, getString(R.string.wrong_credentials), Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG: String = "LoginActivity"

    }
}
