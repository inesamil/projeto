package ps.leic.isel.pt.gis.uis.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_storages.view.*

import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.StorageDTO
import ps.leic.isel.pt.gis.uis.adapters.StoragesAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StoragesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StoragesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StoragesFragment : Fragment() {

    private var houseId: Long = 0
    private lateinit var storages: Array<StorageDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            houseId = it.getLong(ExtraUtils.HOUSE_ID)
        }
        //TODO: get data
        storages = arrayOf(StorageDTO(1, 1, "Fridge", "[0,5]"))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_storages, container, false)

        // Set Adapter
        val adapter = StoragesAdapter(storages)
        view.storagesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.storagesRecyclerView.setHasFixedSize(true)
        view.storagesRecyclerView.adapter = adapter

        return view
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.storages)
    }

    /**
     * StoragesFragment Factory
     */
    companion object {
        val houseIdArg: String = "houseid"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param houseId House ID
         * @return A new instance of fragment StoragesFragment.
         */
        @JvmStatic
        fun newInstance(args: Map<String, Any>) =
                StoragesFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ExtraUtils.HOUSE_ID, args[houseIdArg] as Long)
                    }
                }
    }
}
