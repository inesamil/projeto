package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lists.*
import kotlinx.android.synthetic.main.fragment_lists.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.dtos.ListDto
import ps.leic.isel.pt.gis.model.dtos.ListsDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.ListsAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
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
            url = it.getString(ExtraUtils.URL)
        }
        listsViewModel = ViewModelProviders.of(this).get(ListsViewModel::class.java)
        listsViewModel.init(url)
        listsViewModel.getLists()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
    }

    private fun onSuccess(lists: ListsDto) {
        // Hide progress bar
        listsProgressBar.visibility = View.GONE
        adapter.setData(lists.lists)
        this.lists = lists.lists
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lists, container, false)
        view.listsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.listsRecyclerView.setHasFixedSize(true)
        view.listsRecyclerView.adapter = adapter
        // Set new lists listener
        view.createNewListBtn.setOnClickListener {
            // Listener for new list creation
            listener?.onNewListInteraction()
        }
        view.createNewListText.setOnClickListener {
            // Listener for new list creation
            listener?.onNewListInteraction()
        }
        adapter.setOnItemClickListener(this)
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
        activity?.title = getString(R.string.lists)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ExtraUtils.URL, url)
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
     * Listeners
     ***/

    // Listener for list clicks (from adapter)
    override fun onItemClick(view: View, position: Int) {
        lists?.let {
            val list = it[position]
            list.links.listsLink?.let {
                listener?.onListInteraction(it, list.listName)
            }
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
    }

    /**
     * ListsFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ListsFragment.
         */
        @JvmStatic
        fun newInstance(url: String) =
                ListsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExtraUtils.URL, url)
                    }
                }
    }
}
