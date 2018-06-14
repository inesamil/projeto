package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_basic_information.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.UserDTO
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.viewModel.BasicInformationViewModel

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BasicInformationFragment.OnBasicInformationFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BasicInformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BasicInformationFragment : Fragment() {

    private var listener: OnBasicInformationFragmentInteractionListener? = null
    private lateinit var basicInfoVM: BasicInformationViewModel
    private lateinit var url: String

    private var state: State = State.LOADING
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ConstraintLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBasicInformationFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnBasicInformationFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL_TAG)
        }
        basicInfoVM = ViewModelProviders.of(this).get(BasicInformationViewModel::class.java)
        basicInfoVM.init(url)
        basicInfoVM.getUser()?.observe(this, Observer {
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
        val view: View = inflater.inflate(R.layout.fragment_basic_information, container, false)

        progressBar = view.basicInformationProgressBar
        content = view.basicInformationLayout

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL_TAG, url)
    }

    override fun onStop() {
        super.onStop()
        basicInfoVM.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Auxiliary Methods
     ***/

    private fun onSuccess(user: UserDto) {
        state = State.SUCCESS

        showProgressBarOrContent()

        // Set info
        view?.let {
            it.fullnameText.text = user.name
            it.emailText.text = user.email
            it.requesterUserText.text = user.username
            it.ageText.text = user.age.toString()
        }
    }

    private fun onError(error: String?) {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnBasicInformationFragmentInteractionListener {
        fun onBasicInformationUpdate(user: UserDTO)
    }

    companion object {
        const val TAG: String = "BasicInformationFragment"
        private const val URL_TAG: String = "URL"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param url url
         * @return A new instance of fragment BasicInformationFragment.
         */
        @JvmStatic
        fun newInstance(url: String) =
                BasicInformationFragment().apply {
                    arguments = Bundle().apply {
                        putString(URL_TAG, url)
                    }
                }
    }
}
