package ps.leic.isel.pt.gis.uis.fragments

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.UserDTO
import ps.leic.isel.pt.gis.utils.ExtraUtils

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
    //private lateinit var user: UserDTO

    private var listener: OnBasicInformationFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ExtraUtils.USER_USERNAME)
        }
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
