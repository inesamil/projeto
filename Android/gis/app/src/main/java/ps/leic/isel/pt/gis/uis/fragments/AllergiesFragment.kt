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
class AllergiesFragment : Fragment(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private lateinit var allergiesViewModel: AllergiesViewModel
    private lateinit var url: String
    private val adapter = AllergiesTableAdapter()

    private var state: State = State.LOADING;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ExtraUtils.URL)
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

            // Radio Group Buttons listener
            it.allergiesRadioGroup.setOnCheckedChangeListener(this)

            // Save button listener
            it.allergiesSaveBtn.setOnClickListener(this)
        }
    }

    private fun showProgressBarOrContent() {
        view?.let {
            it.allergiesProgressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
            it.allergiesLayout.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun onError(error: String?) {
        state = State.ERROR
        error?.let {
            Log.v("APP_GIS", it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_allergies, container, false)
        // Set Adapter
        view.allergiesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.allergiesRecyclerView.setHasFixedSize(true)
        view.allergiesRecyclerView.adapter = adapter

        //Show progress bar or content
        showProgressBarOrContent()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(ExtraUtils.URL)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.allergies)
    }

    override fun onPause() {
        super.onPause()
        val mPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        mPreferences.edit().putBoolean(SHOW_ALLERGIES_TAG, allergiesYesRadioBtn.isChecked).apply()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ExtraUtils.URL, url)
    }

    override fun onStop() {
        super.onStop()
        allergiesViewModel.cancel()
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
        //Toast.makeText(view?.context, "Saved Allergies", Toast.LENGTH_SHORT).show()
        Toast.makeText(view?.context, "Functionality Not Yet Available", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val SHOW_ALLERGIES_TAG = "SHOW_ALLERGIES_TAG"

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
                        putString(ExtraUtils.URL, url)
                    }
                }
    }
}
