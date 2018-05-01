package ps.leic.isel.pt.gis.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*
import ps.leic.isel.pt.gis.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signInBtn.setOnClickListener {
            startActivity(Intent(this, ProductCategoryActivity::class.java))
        }
    }
}
