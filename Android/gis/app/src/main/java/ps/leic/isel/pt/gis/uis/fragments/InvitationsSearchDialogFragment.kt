package ps.leic.isel.pt.gis.uis.fragments

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.layout_inivitations_search_dialog.view.*
import ps.leic.isel.pt.gis.GisApplication
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.model.dtos.UsersDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.InvitationsSearchAdapter
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.viewModel.UsersViewModel

class InvitationsSearchDialogFragment : DialogFragment(), InvitationsSearchAdapter.OnItemClickListener, SearchView.OnQueryTextListener {

    private lateinit var usersViewModel: UsersViewModel

    private var users: Array<UserDto>? = null

    private lateinit var adapter: InvitationsSearchAdapter

    private var listener: OnInvitationsSearchDialogFragmentListener? = null

    private var state: State = State.SUCCESS
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ConstraintLayout

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        adapter = InvitationsSearchAdapter(getString(R.string.at_username))

        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(R.layout.layout_inivitations_search_dialog, null)
        builder.setView(view)

        // Set Adapter to Recycler View
        view.usersResultsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.usersResultsRecyclerView.setHasFixedSize(true)
        view.usersResultsRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        progressBar = view.invitationsSearchProgressBar
        content = view.searchResultsLayout

        showProgressBarOrContent()

        view.invitationsSearchView.setOnQueryTextListener(this)

        return builder.create()
    }

    override fun onSendInvitation(username: String) {
        listener?.onSendInvitation(username)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //Nothing to do here
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            state = State.LOADING
            val gisApplication = activity?.applicationContext as GisApplication
            val index = gisApplication.index
            val url = index.getUsersUrl(it)
            url?.let {
                usersViewModel.init(it)
                usersViewModel.getUsers()?.observe(this, Observer {
                    when (it?.status) {
                        Status.SUCCESS -> onSuccess(it.data)
                        Status.UNSUCCESS -> onUnsuccess(it.apiError)
                        Status.ERROR -> onError(it.message)
                    }
                })
                return true
            }
        }
        return false
    }

    fun setOnInvitationsSearchDialogFragmentListener(listener: OnInvitationsSearchDialogFragmentListener) {
        this.listener = listener
    }

    /***
     * Private Methods
     ***/

    private fun onSuccess(users: UsersDto?) {
        users?.let {
            state = State.SUCCESS

            this.users = it.users
            adapter.setData(it.users)

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
        content.visibility = if (state == State.SUCCESS) View.VISIBLE else View.INVISIBLE
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnInvitationsSearchDialogFragmentListener {
        fun onSendInvitation(username: String)
    }

    /**
     * InvitationsSearchDialogFragment Factory
     */
    companion object {
        const val TAG: String = "InvitationsSearchDialog"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment InvitationsSearchDialogFragment.
         */
        @JvmStatic
        fun newInstance() = InvitationsSearchDialogFragment()
    }
}