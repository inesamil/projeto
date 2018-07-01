package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_invitations.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.InvitationDto
import ps.leic.isel.pt.gis.model.dtos.InvitationsDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.InvitationsAdapter
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.utils.removeElement
import ps.leic.isel.pt.gis.viewModel.InvitationsViewModel

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

    private lateinit var url: String
    private lateinit var invitations: Array<InvitationDto>

    private lateinit var invitationsViewModel: InvitationsViewModel

    private lateinit var adapter: InvitationsAdapter

    private var state: State = State.LOADING
    private lateinit var progressBar: ProgressBar
    private lateinit var invitationsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL_TAG)
        }

        adapter = InvitationsAdapter(getString(R.string.at_username), getString(R.string.invitation_question))

        invitationsViewModel = ViewModelProviders.of(this).get(InvitationsViewModel::class.java)
        invitationsViewModel.init(url)
        getInvitations()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_invitations, container, false)

        progressBar = view.invitationsProgressBar
        invitationsRecyclerView = view.invitationsRecyclerView

        // Set Adapter
        invitationsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        invitationsRecyclerView.setHasFixedSize(true)
        invitationsRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        return view
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.invitations)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL_TAG, url)
    }

    override fun onStop() {
        super.onStop()
        invitationsViewModel.cancel()
    }

    /***
     * Private Methods
     ***/

    private fun getInvitations() {
        invitationsViewModel.getInvitations()?.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> onSuccess(it.data)
                Status.UNSUCCESS -> onUnsuccess(it.apiError)
                Status.ERROR -> onError(it.message)
                Status.LOADING -> {
                    state = State.LOADING
                }
            }
        })
    }

    private fun onSuccess(invitations: InvitationsDto?) {
        invitations?.let {
            state = State.SUCCESS

            this.invitations = it.invitations
            adapter.setData(it.invitations)

            // Show progress bar or content
            showProgressBarOrContent()
        }
    }

    private fun onUnsuccess(error: ErrorDto?) {
        state = State.ERROR
        error?.let {
            Log.e(TAG, it.developerErrorMessage)
            onError(it.message)
        }
    }

    private fun onError(message: String?) {
        state = State.ERROR
        message?.let {
            Log.e(TAG, it)
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            fragmentManager?.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    private fun showProgressBarOrContent() {
        progressBar.visibility = if (state == State.LOADING) View.VISIBLE else View.GONE
        invitationsRecyclerView.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
    }

    private fun removeInvitation(invitation: InvitationDto) {
        invitations = invitations.removeElement(invitation)
        adapter.setData(invitations)
    }

    /***
     * Listeners
     ***/

    // Listener for accept invitations
    override fun onAcceptInvitation(invitation: InvitationDto) {
        invitationsViewModel.acceptInvitation(invitation)?.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> removeInvitation(invitation)
                Status.UNSUCCESS -> onUnsuccess(it.apiError)
                Status.ERROR -> onError(it.message)
            }
        })
    }

    // Listener for decline invitations
    override fun onDeclineInvitation(invitation: InvitationDto) {
        invitationsViewModel.declineInvitation(invitation)?.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> removeInvitation(invitation)
                Status.UNSUCCESS -> onUnsuccess(it.apiError)
                Status.ERROR -> onError(it.message)
            }
        })
    }

    /**
     * InvitationsFragment Factory
     */
    companion object {
        const val TAG: String = "InvitationsFragment"
        private const val URL_TAG: String = "URL"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param url URL
         * @return A new instance of fragment InvitationsFragment.
         */
        @JvmStatic
        fun newInstance(url: String) =
                InvitationsFragment().apply {
                    arguments = Bundle().apply {
                        putString(URL_TAG, url)
                    }
                }
    }
}
