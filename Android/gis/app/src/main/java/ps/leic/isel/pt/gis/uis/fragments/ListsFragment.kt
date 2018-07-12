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
import kotlinx.android.synthetic.main.fragment_lists.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.List
import ps.leic.isel.pt.gis.model.body.ListBody
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.ListDto
import ps.leic.isel.pt.gis.model.dtos.ListsDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.ListsAdapter
import ps.leic.isel.pt.gis.utils.State
import ps.leic.isel.pt.gis.utils.addElement
import ps.leic.isel.pt.gis.viewModel.ListsViewModel

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ListsFragment.OnListsFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ListsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ListsFragment : Fragment(), ListsAdapter.OnItemClickListener {

    private var lists: Array<ListDto>? = null

    private val adapter = ListsAdapter()
    private var listener: OnListsFragmentInteractionListener? = null
    private lateinit var listsViewModel: ListsViewModel
    private lateinit var url: String

    private var state: State = State.LOADING
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ConstraintLayout
    private lateinit var emptyLayout: ConstraintLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListsFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListsFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL_TAG)
        }
        listsViewModel = ViewModelProviders.of(activity!!).get(ListsViewModel::class.java)
        listsViewModel.init(url)
        getLists()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the content for this fragment
        val view = inflater.inflate(R.layout.fragment_lists, container, false)
        view.listsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.listsRecyclerView.setHasFixedSize(true)
        view.listsRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
        // Listener for new list creation
        view.newListButton.setOnClickListener {
            listener?.onNewListInteraction()
        }
        view.newListButton.bringToFront()
        // Listener for filters
        view.listsLayout.filtersText.setOnClickListener {
            listener?.onFiltersInteraction()
        }

        progressBar = view.listsProgressBar
        content = view.listsLayout
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
        activity?.title = getString(R.string.lists)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL_TAG, url)
    }

    override fun onStop() {
        super.onStop()
        listsViewModel.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Private Methods
     ***/

    private fun getLists() {
        listsViewModel.getLists()?.observe(this, Observer {
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

    private fun onSuccess(lists: ListsDto?) {
        lists?.let {
            state = State.SUCCESS

            this.lists = it.lists

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
                lists?.let {
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

    // Listener for list clicks (from adapter)
    override fun onItemClick(view: View, position: Int) {
        lists?.let {
            val list = it[position]
            list.links.selfLink?.let {
                listener?.onListInteraction(it, list.listName)
            }
        }
    }

    fun onAddList(list: List) {
        listsViewModel.addList(ListBody(list.houseId, list.listName, list.listShareable))?.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> {
                    it.data?.let { list ->
                        // Add new list
                        lists = lists?.addElement(list)
                        // Notify data set changed
                        lists?.let {
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

    fun onFiltersApplied(systemLists: Boolean, userLists: Boolean, sharedLists: Boolean, houses: Array<HouseDto>?, loggedInUser: String) {
        lists?.filter {
            // List in the house
            val houseId = it.houseId
            if (houses == null) return
            val listInHouse: Boolean = houses.any { it.houseId == houseId }
            if (!listInHouse) return@filter false

            return@filter (systemLists && it.listType == ListDto.SYSTEM_TYPE)    // System lists
                    || (userLists && it.listType == ListDto.USER_TYPE && it.username == loggedInUser)  // From the logged in user lists
                    || (it.username != loggedInUser && it.listType == ListDto.USER_TYPE && it.shareable == sharedLists) // Lists shared by other members
        }?.let {
            adapter.setData(it.toTypedArray())
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnListsFragmentInteractionListener {
        fun onListInteraction(url: String, listName: String)
        fun onNewListInteraction()
        fun onFiltersInteraction()
    }

    /**
     * ListsFragment Factory
     */
    companion object {
        const val TAG: String = "ListsFragment"
        private const val URL_TAG: String = "URL"
        const val URL_ARG: String = "url"
        private const val USERNAME_TAG: String = "USERNAME"
        const val USERNAME_ARG: String = "username"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ListsFragment.
         */
        @JvmStatic
        fun newInstance(args: Map<String, Any>) =
                ListsFragment().apply {
                    arguments = Bundle().apply {
                        putString(URL_TAG, args[URL_ARG] as String)
                        putString(USERNAME_TAG, args[USERNAME_ARG] as String)
                    }
                }
    }
}
