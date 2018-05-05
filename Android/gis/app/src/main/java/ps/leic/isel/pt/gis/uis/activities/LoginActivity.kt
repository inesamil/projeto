package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import ps.leic.isel.pt.gis.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signupBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        signinBtn.setOnClickListener {

            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
