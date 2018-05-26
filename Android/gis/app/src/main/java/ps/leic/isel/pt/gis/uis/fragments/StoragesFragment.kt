package ps.leic.isel.pt.gis.uis.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_storages.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.StorageDTO
import ps.leic.isel.pt.gis.model.TemperatureStorageDTO
import ps.leic.isel.pt.gis.model.dtos.StoragesDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.StoragesAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.RequestQueue
import ps.leic.isel.pt.gis.viewModel.StoragesViewModel

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
    private var storagesViewModel: StoragesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            houseId = it.getLong(ExtraUtils.HOUSE_ID)
        }
        storagesViewModel = ViewModelProviders.of(this).get(StoragesViewModel::class.java)
        // for demo
        val url = "http://10.0.2.2:8081/v1/houses/1"
        storagesViewModel?.init(url)
        storagesViewModel?.getStorages()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
        //TODO: get data
        storages = arrayOf(StorageDTO(1, 1, "Fridge", TemperatureStorageDTO(0F, 5F)))
    }

    private fun onSuccess(storages: StoragesDto) {
        // Set Adapter
        val adapter = StoragesAdapter(storages.storages)
        view?.let {
            it.storagesRecyclerView.layoutManager = LinearLayoutManager(it.context)
            it.storagesRecyclerView.setHasFixedSize(true)
            it.storagesRecyclerView.adapter = adapter
        }
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storages, container, false)
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
