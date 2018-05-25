package ps.leic.isel.pt.gis.uis.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.VolleyError

import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.StorageDTO
import ps.leic.isel.pt.gis.model.TemperatureStorageDTO
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.uis.adapters.StoragesAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.RequestQueue
import ps.leic.isel.pt.gis.utils.Requester

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
        // for demo
        val url = "http://10.0.2.2:8081/v1/houses/1"
        val tagToBeCancelled = "STORAGES_FRAGMENT"

        RequestQueue.getInstance(activity?.applicationContext).addToRequestQueue(
                Requester(Request.Method.GET, url, null, HouseDto::class.java, ::onSuccess, ::onError, tagToBeCancelled)
        )

        //TODO: get data
        storages = arrayOf(StorageDTO(1, 1, "Fridge", TemperatureStorageDTO(0F, 5F)))
    }

    private fun onSuccess(dto: HouseDto) {

    }

    private fun onError(error: VolleyError?) {

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

    override fun onStop() {
        super.onStop()
        RequestQueue.getInstance(activity?.applicationContext).requestQueue.cancelAll("STORAGES_FRAGMENT")
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
