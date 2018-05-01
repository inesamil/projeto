package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_initial_setup1.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.utils.ExtraUtils

class InitialSetup1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_setup1)

        nextBtn.setOnClickListener{
            val intent: Intent = Intent(this, InitialSetup2Activity::class.java)
            intent.putExtra(ExtraUtils.HOUSE_NAME, housesnameEditText.text.toString())
            startActivity(intent)
        }
    }
}
