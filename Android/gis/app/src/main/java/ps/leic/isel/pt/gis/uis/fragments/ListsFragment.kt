package ps.leic.isel.pt.gis.uis.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lists.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListDTO
import ps.leic.isel.pt.gis.model.ListProductDTO
import ps.leic.isel.pt.gis.model.SystemListDTO
import ps.leic.isel.pt.gis.model.UserListDTO
import ps.leic.isel.pt.gis.uis.adapters.ListsAdapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: get data
        lists = arrayOf(
                SystemListDTO(1, 1, "Lista de Compras", "system"),
                UserListDTO(1, 2, "Lista da a Festa da Alice", "user", "bob", true)
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_lists, container, false)

        // Set Adapter
        val adapter = ListsAdapter(lists)
        view.listsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.listsRecyclerView.setHasFixedSize(true)
        view.listsRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        return view
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

    // Listener for category item clicks (from adapter)
    override fun onItemClick(view: View, position: Int) {
        val list: ListDTO = lists[position]
        listener?.onListInteraction(list)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnListsFragmentInteractionListener {
        fun onListInteraction(list: ListDTO)
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
