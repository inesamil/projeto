package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_initial_setup.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.utils.EditTextUtils
import ps.leic.isel.pt.gis.utils.RestrictionsUtils

class InitialSetupActivity : AppCompatActivity() {

    private lateinit var houseName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_setup)

        //houseName = intent.getStringExtra(ExtraUtils.HOUSE_NAME)

        // Set Plus buttons listener
        babiesPlusBtn.setOnClickListener {
            EditTextUtils.incNumberText(
                    babiesNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        childrenPlusBtn.setOnClickListener {
            EditTextUtils.incNumberText(
                    childrenNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        adultsPlusBtn.setOnClickListener {
            EditTextUtils.incNumberText(
                    adultsNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        seniorsPlusBtn.setOnClickListener {
            EditTextUtils.incNumberText(
                    seniorNumEditText,
                    RestrictionsUtils.characteristicsMinValue,
                    RestrictionsUtils.characteristicsMaxValue)
        }

        // Set Minus buttons listener
        babiesMinusBtn.setOnClickListener {
            EditTextUtils.decNumberText(
                    babiesNumEditText,
                    RestrictionsUtils.characteristicsMinValue)
        }
        childrenMinusBtn.setOnClickListener {
            EditTextUtils.decNumberText(
                    childrenNumEditText,
                    RestrictionsUtils.characteristicsMinValue)
        }

        adultsMinusBtn.setOnClickListener {
            EditTextUtils.decNumberText(
                    adultsNumEditText,
                    RestrictionsUtils.characteristicsMinValue)
        }

        seniorsMinusBtn.setOnClickListener {
            EditTextUtils.decNumberText(
                    seniorNumEditText,
                    RestrictionsUtils.characteristicsMinValue)
        }
        // Set start button listener
        startBtn.setOnClickListener {
            //TODO: addHouse()
            //TODO: addMember()
            finish()
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
