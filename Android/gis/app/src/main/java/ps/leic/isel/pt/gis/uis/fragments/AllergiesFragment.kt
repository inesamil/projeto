package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_allergies.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.body.HouseAllergiesBody
import ps.leic.isel.pt.gis.model.body.HouseAllergyBody
import ps.leic.isel.pt.gis.model.dtos.HouseAllergiesDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.AllergiesTableAdapter
import ps.leic.isel.pt.gis.utils.State
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
class AllergiesFragment : Fragment() {

    private lateinit var allergiesViewModel: AllergiesViewModel
    private lateinit var url: String
    private val adapter = AllergiesTableAdapter()

    private var state: State = State.LOADING
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ConstraintLayout

    // Buttons
    private lateinit var yesRadioButton: RadioButton
    private lateinit var noRadioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL_TAG)
        }
        allergiesViewModel = ViewModelProviders.of(this).get(AllergiesViewModel::class.java)
        url.let {
            allergiesViewModel.init(it)
        }
        allergiesViewModel.getAllergies()?.observe(this, Observer {
            when {
                it?.status == Status.SUCCESS -> onSuccess(it.data!!)
                it?.status == Status.ERROR -> onError(it.message)
                it?.status == Status.LOADING -> {
                    state = State.LOADING
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_allergies, container, false)
        // Set Adapter
        view.allergiesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.allergiesRecyclerView.setHasFixedSize(true)
        view.allergiesRecyclerView.adapter = adapter

        // Set save button listener
        view.allergiesSaveBtn.setOnClickListener {
            saveAllergies()
        }

        // Radio Group Buttons listener
        view.allergiesRadioGroup.setOnCheckedChangeListener(::onCheckedChanged)

        progressBar = view.allergiesProgressBar
        content = view.allergiesLayout

        yesRadioButton = view.allergiesYesRadioBtn
        noRadioButton = view.allergiesNoRadioBtn

        //Show progress bar or content
        showProgressBarOrContent()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(URL_TAG)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.allergies)
    }

    override fun onPause() {
        super.onPause()
        val mPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        mPreferences.edit().putBoolean(SHOW_ALLERGIES_TAG, yesRadioButton.isChecked).apply()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL_TAG, url)
    }

    override fun onStop() {
        super.onStop()
        allergiesViewModel.cancel()
    }

    /***
     * Auxiliary Methods
     ***/

    private fun onSuccess(allergies: HouseAllergiesDto) {
        state = State.SUCCESS

        // Hide progress bar
        showProgressBarOrContent()

        // Set data to adapter
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
        }
    }

    private fun onError(error: String?) {
        state = State.ERROR
        error?.let {
            Log.v("APP_GIS", it)
        }
    }

    private fun showProgressBarOrContent() {
        progressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
        content.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
    }

    /***
     * Listeners
     ***/

    private fun saveAllergies() {
        if (noRadioButton.isChecked) {
            allergiesViewModel.deleteAllHouseAllergies()?.observe(this, Observer {
                when {
                    it?.status == Status.SUCCESS -> {
                        Toast.makeText(context, getString(R.string.allergies_saved_successfully), Toast.LENGTH_SHORT).show()
                    }
                    it?.status == Status.ERROR -> {
                        Toast.makeText(context, getString(R.string.could_not_save_allergies), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else {
            adapter.getHouseAllergyItems()?.map { houseAllergy -> HouseAllergyBody(houseAllergy.allergy, houseAllergy.allergics) }?.let {
                allergiesViewModel.addHouseAllergies(HouseAllergiesBody(it.toTypedArray()))?.observe(this, Observer {
                    when {
                        it?.status == Status.SUCCESS -> {
                            Toast.makeText(context, getString(R.string.allergies_saved_successfully), Toast.LENGTH_SHORT).show()
                        }
                        it?.status == Status.ERROR -> {
                            Toast.makeText(context, getString(R.string.could_not_save_allergies), Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    // Listener for radio group buttons clicks
    private fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
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

    /**
     * AllergiesFragment Factory
     */
    companion object {
        const val TAG: String = "AllergiesFragment"
        private const val SHOW_ALLERGIES_TAG = "SHOW_ALLERGIES_TAG"
        private const val URL_TAG: String = "URL"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param url url
         * @return A new instance of fragment AllergiesFragment.
         */
        @JvmStatic
        fun newInstance(url: String) =
                AllergiesFragment().apply {
                    arguments = Bundle().apply {
                        putString(URL_TAG, url)
                    }
                }
    }
}
