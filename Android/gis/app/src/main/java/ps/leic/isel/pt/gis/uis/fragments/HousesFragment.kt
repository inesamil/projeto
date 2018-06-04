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
import kotlinx.android.synthetic.main.fragment_houses.*
import kotlinx.android.synthetic.main.fragment_houses.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.HousesAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.viewModel.HousesViewModel

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

    private lateinit var housesViewModel: HousesViewModel
    private lateinit var url: String

    private var houses: HousesDto? = null
    private val adapter = HousesAdapter()
    private var listener: OnHousesFragmentInteractionListener? = null

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
            url = it.getString(ExtraUtils.URL)
        }
        housesViewModel = ViewModelProviders.of(this).get(HousesViewModel::class.java)
        housesViewModel.init(url)
        housesViewModel.getHouses()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
            else if (it?.status == Status.LOADING){
                housesProgressBar.visibility = View.VISIBLE
                housesLayout.visibility = View.INVISIBLE
            }
        })
    }

    private fun onSuccess(houses: HousesDto) {
        // Hide progress bar
        housesProgressBar.visibility = View.GONE
        housesLayout.visibility = View.VISIBLE

        this.houses = houses
        adapter.setData(houses.houses)
    }

    private fun onError(error: String?) {
        error?.let {
            Log.v("APP_GIS", error)
        }
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
            houses?.actions?.addHouse?.let {
                listener?.onNewHouseInteraction()
            }
        }
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

    override fun onStop() {
        super.onStop()
        housesViewModel.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Listeners
     ***/

    override fun onStoragesClick(view: View, position: Int) {
        houses?.houses?.get(position)?.links?.storagesLink?.let {
            listener?.onStoragesInteraction(it)
        }
    }

    override fun onAllergiesClick(view: View, position: Int) {
        houses?.houses?.get(position)?.links?.houseAllergiesLink?.let {
            listener?.onAllergiesInteraction(it)
        }
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
    }

    /**
     * HousesFragment Factory
     */
    companion object {
        const val TAG: String = "HousesFragment"
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
                        putString(ExtraUtils.URL, url)
                    }
                }
    }
}
