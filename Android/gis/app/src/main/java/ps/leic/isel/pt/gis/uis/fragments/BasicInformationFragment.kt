package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_basic_information.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.UserDTO
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.utils.ExtraUtils
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

    private lateinit var username: String
    private lateinit var user: UserDTO

    private var listener: OnBasicInformationFragmentInteractionListener? = null
    private var basicInfoVM: BasicInformationViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ExtraUtils.USER_USERNAME)
        }
        basicInfoVM = ViewModelProviders.of(this).get(BasicInformationViewModel::class.java)
        val url = ""
        basicInfoVM?.init(url)
        basicInfoVM?.getUser()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
        //TODO: get data
        user = UserDTO("alice", "alice@example.com", 20, "Alice Smith")
    }

    private fun onSuccess(user: UserDto) {
        // Set info
        view?.let {
            UserDTO
            it.fullnameText.text = user.name
            it.emailText.text = user.email
            it.requesterUserText.text = user.username
            it.ageText.text = user.age.toString()
        }
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic_information, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBasicInformationFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnBasicInformationFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param username Username
         * @return A new instance of fragment BasicInformationFragment.
         */
        @JvmStatic
        fun newInstance(username: String) =
                BasicInformationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExtraUtils.USER_USERNAME, username)
                    }
                }
    }
}
