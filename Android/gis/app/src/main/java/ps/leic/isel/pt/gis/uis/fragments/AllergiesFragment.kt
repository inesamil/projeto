package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_allergies.*
import kotlinx.android.synthetic.main.fragment_allergies.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.HouseAllergiesDto
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

    private var allergiesViewModel: AllergiesViewModel? = null
    private var url: String? = null
    private val adapter = AllergiesTableAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("APP_GIS", "On create")
        url?.let {
            Log.d("APP_GIS", it)
        }
        arguments?.let {
            url = it.getString(ExtraUtils.URL)
        }
        allergiesViewModel = ViewModelProviders.of(this).get(AllergiesViewModel::class.java)
        url?.let {
            allergiesViewModel?.init(it)
        }
        allergiesViewModel?.getAllergies()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
    }

    private fun onSuccess(allergies: HouseAllergiesDto) {
        adapter.setData(allergies.houseAllergies)

        // Show allergies or not
        view?.let {
            val mPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
            val showAllergies = mPreferences.getBoolean(SHOW_ALLERGIES_TAG, true)
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
        Log.d("APP_GIS", "On create view")
        url?.let {
            Log.d("APP_GIS", it)
        }
        val view = inflater.inflate(R.layout.fragment_allergies, container, false)
        // Set Adapter
        view.allergiesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.allergiesRecyclerView.setHasFixedSize(true)
        view.allergiesRecyclerView.adapter = adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("APP_GIS", "On activity created")
        url?.let {
            Log.d("APP_GIS", it)
        }
        url = savedInstanceState?.getString(ExtraUtils.URL)
    }

    override fun onStart() {
        super.onStart()
        Log.d("APP_GIS", "On start")
        url?.let {
            Log.d("APP_GIS", it)
        }
        activity?.title = getString(R.string.allergies)
    }

    override fun onPause() {
        super.onPause()
        Log.d("APP_GIS", "On pause")
        url?.let {
            Log.d("APP_GIS", it)
        }
        val mPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        mPreferences.edit().putBoolean(SHOW_ALLERGIES_TAG, allergiesYesRadioBtn.isChecked).apply()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("APP_GIS", "On save instance state")
        url?.let {
            Log.d("APP_GIS", it)
        }
        outState.putString(ExtraUtils.URL, url)
    }

    /***
     * Listeners
     ***/

    // NfcListener for radio group buttons clicks
    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        view?.let {
            when (checkedId) {
                R.id.allergiesYesRadioBtn -> {
                    it.allergiesRecyclerView.visibility = View.VISIBLE
                    it.allergiesRadioGroup.check(R.id.allergiesYesRadioBtn)
                }
                R.id.allergiesNoRadioBtn -> {
                    it.allergiesRecyclerView.visibility = View.INVISIBLE
                    it.allergiesRadioGroup.check(R.id.allergiesNoRadioBtn)
                }
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
        private const val SHOW_ALLERGIES_TAG = "SHOW_ALLERGIES_TAG"

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
