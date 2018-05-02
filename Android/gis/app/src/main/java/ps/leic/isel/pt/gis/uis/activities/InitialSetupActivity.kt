package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_initial_setup.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.utils.ExtraUtils

class InitialSetupActivity : AppCompatActivity() {

    private lateinit var houseName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_setup)

        houseName = intent.getStringExtra(ExtraUtils.HOUSE_NAME)

        startBtn.setOnClickListener {
            //TODO: addHouse()
            //TODO: addMember()
            finish()
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
