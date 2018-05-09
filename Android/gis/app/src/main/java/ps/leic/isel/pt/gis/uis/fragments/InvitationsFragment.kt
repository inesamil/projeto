package ps.leic.isel.pt.gis.uis.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_invitations.view.*

import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.InvitationDTO
import ps.leic.isel.pt.gis.uis.adapters.InvitationsAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [InvitationsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [InvitationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class InvitationsFragment : Fragment(), InvitationsAdapter.OnItemClickListener {

    private lateinit var username: String
    private lateinit var invitations: Array<InvitationDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ExtraUtils.USER_USERNAME)
        }
        //TODO: get Data
        invitations = arrayOf(InvitationDTO("bob", 1, "Smith"))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_invitations, container, false)

        view.invitationsSearch.setOnSearchClickListener {
            //TODO
        }

        // Set Adapter
        val adapter = InvitationsAdapter(invitations)
        view.invitationsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.invitationsRecyclerView.setHasFixedSize(true)
        view.invitationsRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        return view
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.invitations)
    }

    /***
     * Listeners
     ***/

    // Listener for accept invitations
    override fun onAcceptInvitation(invitation: InvitationDTO) {
        //TODO: accept invitation
    }

    // Listener for decline invitations
    override fun onDeclineInvitation(invitation: InvitationDTO) {
        // TODO: decline invitation
        // nothing to do ?
    }

    /**
     * InvitationsFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param username Username
         * @return A new instance of fragment InvitationsFragment.
         */
        @JvmStatic
        fun newInstance(username: String) =
                InvitationsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExtraUtils.USER_USERNAME, username)
                    }
                }
    }
}
