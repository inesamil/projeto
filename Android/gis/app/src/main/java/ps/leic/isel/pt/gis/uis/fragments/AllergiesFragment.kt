package ps.leic.isel.pt.gis.uis.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_allergies.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.HouseAllergyDTO
import ps.leic.isel.pt.gis.uis.adapters.AllergiesTableAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AllergiesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AllergiesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AllergiesFragment : Fragment(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private var houseId: Long = 0
    private var showAllergies: Boolean = false
    private lateinit var allergies: Array<HouseAllergyDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            showAllergies = it.getBoolean(ExtraUtils.SHOW_ALLERGIES)
            houseId = it.getLong(ExtraUtils.HOUSE_ID)
        }
        //TODO: get data
        allergies = arrayOf(
                HouseAllergyDTO(1, "Lactose", 1),
                HouseAllergyDTO(1, "Glúten", 1)
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_allergies, container, false)

        // Set Adapter
        val adapter = AllergiesTableAdapter(allergies)
        view.allergiesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.allergiesRecyclerView.setHasFixedSize(true)
        view.allergiesRecyclerView.adapter = adapter

        // Show allergies or not
        if (showAllergies) {
            view.allergiesRecyclerView.visibility = View.VISIBLE
            view.allergiesRadioGroup.check(R.id.allergiesYesRadioBtn)
        } else {
            view.allergiesRecyclerView.visibility = View.INVISIBLE
            view.allergiesRadioGroup.check(R.id.allergiesNoRadioBtn)
        }

        // Radio Group Buttons listener
        view.allergiesRadioGroup.setOnCheckedChangeListener(this)

        // Save button listener
        view.allergiesSaveBtn.setOnClickListener(this)

        return view
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.allergies)
    }

    /***
     * Listeners
     ***/

    // NfcListener for radio group buttons clicks
    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when(checkedId) {
            R.id.allergiesYesRadioBtn -> {
                view!!.allergiesRecyclerView.visibility = View.VISIBLE
                view!!.allergiesRadioGroup.check(R.id.allergiesYesRadioBtn)
            }
            R.id.allergiesNoRadioBtn -> {
                view!!.allergiesRecyclerView.visibility = View.INVISIBLE
                view!!.allergiesRadioGroup.check(R.id.allergiesNoRadioBtn)
            }
        }
    }

    // NfcListener for save button clicks
    override fun onClick(v: View?) {
        // TODO: save allergies
        Toast.makeText(view?.context, "Saved Allergies", Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param houseId House ID
         * @param showAllergies boolean show or not allergies
         * @return A new instance of fragment AllergiesFragment.
         */
        @JvmStatic
        fun newInstance(houseId: Long, showAllergies: Boolean) =
                AllergiesFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ExtraUtils.HOUSE_ID, houseId)
                        putBoolean(ExtraUtils.SHOW_ALLERGIES, showAllergies)
                    }
                }
    }
}
