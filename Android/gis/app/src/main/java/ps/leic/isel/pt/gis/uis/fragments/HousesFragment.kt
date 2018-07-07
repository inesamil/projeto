package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_houses.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.House
import ps.leic.isel.pt.gis.model.body.HouseBody
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.HousesAdapter
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.utils.addElement
import ps.leic.isel.pt.gis.viewModel.HousesViewModel
import ps.leic.isel.pt.gis.viewModel.InvitationsViewModel

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HousesFragment.OnHousesFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HousesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HousesFragment : Fragment(), HousesAdapter.OnItemClickListener {

    private lateinit var url: String
    private lateinit var housesViewModel: HousesViewModel
    private var invitationsViewModel: InvitationsViewModel? = null

    private var houses: Array<HouseDto>? = null
    private lateinit var adapter: HousesAdapter
    private var listener: OnHousesFragmentInteractionListener? = null

    private var state: State = State.LOADING
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ConstraintLayout
    private lateinit var emptyLayout: ConstraintLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHousesFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnHousesFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL_TAG)
        }

        val username = context?.let { ServiceLocator.getCredentialsStore(it).getUsername() } ?: ""

        adapter = HousesAdapter(username, getString(R.string.at_username))
        housesViewModel = ViewModelProviders.of(activity!!).get(HousesViewModel::class.java)
        housesViewModel.init(url)
        getHouses()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_houses, container, false)
        view.housesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.housesRecyclerView.setHasFixedSize(true)
        view.housesRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
        view.housesLayout.newHouseButton.setOnClickListener {
            listener?.onNewHouseInteraction()
        }

        progressBar = view.housesProgressBar
        content = view.housesLayout
        emptyLayout = view.emptyLayout

        // Show progress bar or content
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
        activity?.title = getString(R.string.houses)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL_TAG, url)
    }

    override fun onStop() {
        super.onStop()
        housesViewModel.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * Methods
     */
    fun onAddHouse(house: House) {
        housesViewModel.addHouse(HouseBody(house.houseName, house.babiesNumber, house.childrenNumber, house.adultsNumber, house.seniorsNumber))?.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> {
                    it.data?.let { house ->
                        // Add new house
                        houses = houses?.addElement(house)
                        // Notify data set changed
                        houses?.let {
                            adapter.setData(it)
                        }
                    }
                }
                Status.UNSUCCESS -> onUnsuccess(it.apiError)
                Status.ERROR -> onError(it.message)
                Status.LOADING -> {
                    state = State.LOADING
                }
            }
        })
    }

    fun onInvitationSend(house: HouseDto, username: String) {
        invitationsViewModel = ViewModelProviders.of(this).get(InvitationsViewModel::class.java)
        invitationsViewModel?.sendInvitation(house, username)?.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> Toast.makeText(context, getString(R.string.invitation_sent_successfully), Toast.LENGTH_SHORT).show()
                Status.UNSUCCESS -> onUnsuccess(it.apiError)
                Status.ERROR -> onError(it.message)
            }
        })
    }

    /***
     * Auxiliary Methods
     ***/

    private fun getHouses() {
        housesViewModel.getHouses()?.observe(this, Observer {
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

    private fun onSuccess(houses: HousesDto?) {
        houses?.let {
            state = State.SUCCESS

            this.houses = houses.houses

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
        when (state) {
            State.LOADING -> {
                progressBar.visibility = View.VISIBLE
                content.visibility = View.INVISIBLE
            }
            State.SUCCESS -> {
                progressBar.visibility = View.GONE
                content.visibility = View.VISIBLE
                // Show houses or hint
                houses?.let {
                    if (it.isEmpty()) {
                        emptyLayout.visibility = View.VISIBLE
                    } else {
                        emptyLayout.visibility = View.GONE
                        adapter.setData(it)
                    }
                }
            }
        }
    }

    /***
     * Listeners
     ***/

    override fun onStoragesClick(view: View, position: Int) {
        houses?.get(position)?.links?.storagesLink?.let {
            listener?.onStoragesInteraction(it)
        }
    }

    override fun onAllergiesClick(view: View, position: Int) {
        houses?.get(position)?.links?.houseAllergiesLink?.let {
            listener?.onAllergiesInteraction(it)
        }
    }

    override fun onInvitationClick(house: HouseDto) {
        listener?.onInvitationsSearchInteraction(house)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnHousesFragmentInteractionListener {
        fun onStoragesInteraction(storagesUrl: String)
        fun onAllergiesInteraction(allergiesUrl: String)
        fun onNewHouseInteraction()
        fun onInvitationsSearchInteraction(house: HouseDto)
    }

    /**
     * HousesFragment Factory
     */
    companion object {
        const val TAG: String = "HousesFragment"
        private const val URL_TAG: String = "URL"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param url url
         * @return A new instance of fragment HousesFragment.
         */
        @JvmStatic
        fun newInstance(url: String) =
                HousesFragment().apply {
                    arguments = Bundle().apply {
                        putString(URL_TAG, url)
                    }
                }
    }
}
