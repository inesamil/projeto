package ps.leic.isel.pt.gis.uis.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.uis.adapters.AllergiesTableAdapter

class AllergiesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var allergensNumber: Array<Any>

    // Radio Buttons
    private lateinit var radioGroup: RadioGroup
    private lateinit var yesRadioButton: RadioButton
    private lateinit var noRadioButton: RadioButton

    // Keys
    private val CHECKED_RADIO_BUTTON_TAG: String = "CHECKED RADIO BUTTON"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allergies)

        allergensNumber = arrayOf("Eggs", "Shellfish", "Soy")

        viewManager = LinearLayoutManager(this)
        viewAdapter = AllergiesTableAdapter(allergensNumber)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view_allergies).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        // Init Radio Buttons
        radioGroup = findViewById(R.id.allergiesRadioGroup)
        yesRadioButton = findViewById(R.id.allergiesYesRadioBtn)
        noRadioButton = findViewById(R.id.allergiesNoRadioBtn)

        // Listeners
        yesRadioButton.setOnClickListener {
            recyclerView.visibility = View.VISIBLE
            recyclerView.invalidate()
            //TODO: load data
        }
        noRadioButton.setOnClickListener {
            recyclerView.visibility = View.INVISIBLE
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        when (radioGroup.checkedRadioButtonId) {
            yesRadioButton.id -> {
                recyclerView.visibility = View.VISIBLE
                recyclerView.invalidate()
                //TODO: load data
            }
            noRadioButton.id -> {
                recyclerView.visibility = View.INVISIBLE
            }
        }
    }
}
