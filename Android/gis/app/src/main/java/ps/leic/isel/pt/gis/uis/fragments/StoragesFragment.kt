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

    private lateinit var url: String
    private lateinit var storagesViewModel: StoragesViewModel
    private val adapter = StoragesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ExtraUtils.URL)
        }
        storagesViewModel = ViewModelProviders.of(this).get(StoragesViewModel::class.java)
        // for demo
        val url = "http://10.0.2.2:8081/v1/houses/1"    //TODO: delete
        storagesViewModel.init(url)
        storagesViewModel.getStorages()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_storages, container, false)

        view.storagesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.storagesRecyclerView.setHasFixedSize(true)
        view.storagesRecyclerView.adapter = adapter

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            url = it.getString(ExtraUtils.URL)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ExtraUtils.URL, url)
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.storages)
    }

    override fun onStop() {
        super.onStop()
        storagesViewModel.cancel()
    }

    private fun onSuccess(storages: StoragesDto) {
        adapter.setData(storages.storages)
    }

    private fun onError(error: String?) {
        Log.v("APP_GIS", error)
    }

    /**
     * StoragesFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param url URL
         * @return A new instance of fragment StoragesFragment.
         */
        @JvmStatic
        fun newInstance(url: String) =
                StoragesFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExtraUtils.URL, url)
                    }
                }
    }
}
