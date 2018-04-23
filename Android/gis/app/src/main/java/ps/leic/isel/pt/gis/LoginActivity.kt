package ps.leic.isel.pt.gis

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signupBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        alreadyHouseBtn.setOnClickListener {
            startActivity(Intent(this, InitialSetup1Activity::class.java))
        }

        aboutTextClickable.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}
