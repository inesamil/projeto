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
import kotlinx.android.synthetic.main.fragment_houses.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.CharacteristicsDTO
import ps.leic.isel.pt.gis.model.HouseDTO
import ps.leic.isel.pt.gis.model.MemberDTO
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

    private lateinit var username: String
    private lateinit var houses: Array<HouseDTO>

    private var listener: OnHousesFragmentInteractionListener? = null
    private var housesVM: HousesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ExtraUtils.USER_USERNAME)
        }
        housesVM = ViewModelProviders.of(this).get(HousesViewModel::class.java)
        val url = "http://10.0.2.2:8081/v1/users/ze/houses"
        housesVM?.init(url)
        housesVM?.getHouses()?.observe(this, Observer {
            if (it?.status == Status.SUCCESS)
                onSuccess(it.data!!)
            else if (it?.status == Status.ERROR)
                onError(it.message)
        })

        //TODO: get data
        houses = arrayOf(
                HouseDTO(1, "Smith", CharacteristicsDTO(0, 0, 2, 0),
                        arrayOf(MemberDTO(1, "alice", true),
                                MemberDTO(1, "bob", false)))
        )
    }

    private fun onSuccess(houses: HousesDto) {
        // Set Adapter
        val adapter = HousesAdapter(houses.houses)
        view?.let {
            it.housesRecyclerView.layoutManager = LinearLayoutManager(it.context)
            it.housesRecyclerView.setHasFixedSize(true)
            it.housesRecyclerView.adapter = adapter
            // Set new house button listener
            it.newHouseBtn.setOnClickListener {
                val house = HouseDTO(2, "Jones", CharacteristicsDTO(0, 0, 1, 0),
                        arrayOf(MemberDTO(1, "alice", true)))
                //TODO: get new House
                listener?.onNewHouseInteraction(house)
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
        return inflater.inflate(R.layout.fragment_houses, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHousesFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnHousesFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Listeners
     ***/

    override fun onStoragesClick(houseId: Long) {
        listener?.onStoragesInteraction(houseId)
    }

    override fun onAllergiesClick(houseId: Long) {
        listener?.onAllergiesInteraction(houseId)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnHousesFragmentInteractionListener {
        fun onStoragesInteraction(houseId: Long)
        fun onAllergiesInteraction(houseId: Long)
        fun onNewHouseInteraction(house: HouseDTO)
    }

    /**
     * HousesFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param username Username
         * @return A new instance of fragment HousesFragment.
         */
        @JvmStatic
        fun newInstance(username: String) =
                HousesFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExtraUtils.USER_USERNAME, username)
                    }
                }
    }
}
