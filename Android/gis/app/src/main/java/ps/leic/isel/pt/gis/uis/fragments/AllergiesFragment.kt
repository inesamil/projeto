package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_allergies.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.HouseAllergyDTO
import ps.leic.isel.pt.gis.model.dtos.AllergiesDto
import ps.leic.isel.pt.gis.model.dtos.HouseAllergiesDto
import ps.leic.isel.pt.gis.model.dtos.HouseAllergyDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.AllergiesTableAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.viewModel.AllergiesViewModel

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
    private lateinit var allergies: HouseAllergiesDto
    private var allergiesViewModel: AllergiesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            showAllergies = it.getBoolean(ExtraUtils.SHOW_ALLERGIES)
            houseId = it.getLong(ExtraUtils.HOUSE_ID)
        }
        allergiesViewModel = ViewModelProviders.of(this).get(AllergiesViewModel::class.java)
        val url = ""
        allergiesViewModel?.init(url)
        allergiesViewModel?.getAllergies()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
        //TODO: get data
        /*allergies = arrayOf(
                HouseAllergyDTO(1, "Lactose", 1),
                HouseAllergyDTO(1, "Glúten", 1)
        )*/
    }

    private fun onSuccess(allergies: HouseAllergiesDto) {
        // Set Adapter
        val adapter = AllergiesTableAdapter(allergies.houseAllergies)
        view?.let {
            it.allergiesRecyclerView.layoutManager = LinearLayoutManager(it.context)
            it.allergiesRecyclerView.setHasFixedSize(true)
            it.allergiesRecyclerView.adapter = adapter

            // Show allergies or not
            if (showAllergies) {
                it.allergiesRecyclerView.visibility = View.VISIBLE
                it.allergiesRadioGroup.check(R.id.allergiesYesRadioBtn)
            } else {
                it.allergiesRecyclerView.visibility = View.INVISIBLE
                it.allergiesRadioGroup.check(R.id.allergiesNoRadioBtn)
            }

            // Radio Group Buttons listener
            it.allergiesRadioGroup.setOnCheckedChangeListener(this)

            // Save button listener
            it.allergiesSaveBtn.setOnClickListener(this)
        }
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_allergies, container, false)
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
        when (checkedId) {
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
        val houseIdArg: String = "houseid"
        val showAllergiesArg: String = "showallergies"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param houseId House ID
         * @param showAllergies boolean show or not allergies
         * @return A new instance of fragment AllergiesFragment.
         */
        @JvmStatic
        fun newInstance(args: Map<String, Any>) =
                AllergiesFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ExtraUtils.HOUSE_ID, args[houseIdArg] as Long)
                        putBoolean(ExtraUtils.SHOW_ALLERGIES, args[showAllergiesArg] as Boolean)
                    }
                }
    }
}
