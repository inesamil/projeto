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
import kotlinx.android.synthetic.main.fragment_lists.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListDTO
import ps.leic.isel.pt.gis.model.SystemListDTO
import ps.leic.isel.pt.gis.model.UserListDTO
import ps.leic.isel.pt.gis.model.dtos.ListsDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.ListsAdapter
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

    private lateinit var lists: Array<ListDTO>

    private var listener: OnListsFragmentInteractionListener? = null
    private var listsViewModel: ListsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listsViewModel = ViewModelProviders.of(this).get(ListsViewModel::class.java)
        val url = ""
        listsViewModel?.init(url)
        listsViewModel?.getLists()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })

        // TODO: get data
        lists = arrayOf(
                SystemListDTO(1, 1, "Lista de Compras", "system"),
                UserListDTO(1, 2, "Lista para a Festa da Alice", "user", "bob", true)
        )
    }

    private fun onSuccess(lists: ListsDto) {
        // Set Adapter
        val adapter = ListsAdapter(lists.lists)
        view?.let {
            it.listsRecyclerView.layoutManager = LinearLayoutManager(it.context)
            it.listsRecyclerView.setHasFixedSize(true)
            it.listsRecyclerView.adapter = adapter
            // Set new lists listener
            it.createNewListBtn.setOnClickListener {
                onNewListClick()
            }
            it.createNewListText.setOnClickListener {
                onNewListClick()
            }
        }
        adapter.setOnItemClickListener(this)
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.lists)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListsFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListsFragmentInteractionListener")
        }
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
        val list: ListDTO = lists[position]
        listener?.onListInteraction(list)
    }

    // Listener for new list creation
    fun onNewListClick() {
        listener?.onNewListInteraction()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnListsFragmentInteractionListener {
        fun onListInteraction(list: ListDTO)
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
        fun newInstance() = ListsFragment()
    }
}
