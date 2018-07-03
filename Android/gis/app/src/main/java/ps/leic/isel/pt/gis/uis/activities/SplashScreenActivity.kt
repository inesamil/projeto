package ps.leic.isel.pt.gis.uis.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import ps.leic.isel.pt.gis.GisApplication
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.IndexDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.stores.CredentialsStore
import ps.leic.isel.pt.gis.viewModel.SplashScreenViewModel
import ps.leic.isel.pt.gis.viewModel.UserViewModel

class SplashScreenActivity : AppCompatActivity() {

    private var firstTime: Boolean? = null
    private lateinit var splashScreenViewModel: SplashScreenViewModel
    private var userViewModel: UserViewModel? = null

    private var retry: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val gisApplication = application as GisApplication
        if (!gisApplication.isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show()
            return
        }

        splashScreenViewModel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)
        splashScreenViewModel.init()
        getIndex()
    }

    override fun onResume() {
        super.onResume()

        if (retry < MAX_RETRY) {
            val gisApplication = application as GisApplication
            if (!gisApplication.isNetworkAvailable()) {
                val handler = Handler()
                val run = Runnable {
                    ++retry
                    getIndex()
                }
                handler.postDelayed(run, SPLASH_SCREEN_DELAY_IN_MS)
                return
            }
        }
    }

    private fun onSuccess(index: IndexDto?) {
        index?.let {
            val gisApplication = application as GisApplication
            gisApplication.index = it

            if (isFirstTime()) {
                // First time using the app
                Log.i(TAG, "First time using the app.")
                val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                mPreferences.edit().putBoolean(FIRST_TIME_TAG, false).apply()
                finish()
                startActivity(Intent(this, RegisterActivity::class.java))
            } else {
                // Already used the app
                Log.d(TAG, "Try to retrieve credentials.")
                val credentials = ServiceLocator.getCredentialsStore(applicationContext).getCredentials()
                credentials?.let {
                    Log.d(TAG, "Credentials retrieved.")
                    validateCredentials(it)
                }
                // No credentials retrieved
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    private fun onError(message: String?) {
        message?.let {
            Log.i(TAG, it)
        }
        Toast.makeText(this, getString(R.string.could_not_connect_to_server), Toast.LENGTH_SHORT).show()
        if (retry >= MAX_RETRY)
            finish()
    }

    private fun getIndex() {
        splashScreenViewModel.getIndex()?.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> onSuccess(it.data)
                Status.UNSUCCESS -> onUnsuccess(it.apiError)
                Status.ERROR -> onError(it.message)
            }
        })
    }

    private fun validateCredentials(credentials: CredentialsStore.Credentials) {
        val gisApplication = application as GisApplication
        gisApplication.index

        val url = gisApplication.index.getUserUrl(credentials.username)

        url?.let {
            userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
            userViewModel?.init(it)
            userViewModel?.getUser()?.observe(this, Observer {
                when (it?.status) {
                    Status.SUCCESS -> {
                        // Valid Credentials
                        Log.i(TAG, "Retrieved valid credentials, so logged in user.")
                        finish()
                        startActivity(Intent(this, HomeActivity::class.java))
                    }
                    Status.UNSUCCESS -> {
                        // Invalid Credentials
                        Log.d(TAG, "Retrieved invalid credentials, so delete retrieved credential.")
                        ServiceLocator.getCredentialsStore(applicationContext).deleteCredentials()
                        onUnsuccess(it.apiError)
                    }
                    Status.ERROR -> {
                        // Invalid Credentials
                        Log.d(TAG, "Retrieved invalid credentials, so delete retrieved credential.")
                        ServiceLocator.getCredentialsStore(applicationContext).deleteCredentials()
                        onError(it.message)
                    }
                }
            })
        }
    }

    private fun onUnsuccess(error: ErrorDto?) {
        error?.let {
            Log.e(TAG, it.developerErrorMessage)
            onError(it.message)
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
        private const val TAG: String = "SplashScreenActivity"
        private const val FIRST_TIME_TAG = "first_time"
        private const val MAX_RETRY = 1
        private const val SPLASH_SCREEN_DELAY_IN_MS: Long = 3000
    }
}
