package ps.leic.isel.pt.gis.uis.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_houses.view.*

import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.CharacteristicsDTO
import ps.leic.isel.pt.gis.model.HouseDTO
import ps.leic.isel.pt.gis.model.MemberDTO
import ps.leic.isel.pt.gis.uis.adapters.HousesAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ExtraUtils.USER_USERNAME)
        }
        //TODO: get data
        houses = arrayOf(
                HouseDTO(1, "Smith", CharacteristicsDTO(0, 0, 2, 0),
                        arrayOf(MemberDTO(1, "alice", true),
                                MemberDTO(1, "bob", false)))
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_houses, container, false)

        // Set Adapter
        val adapter = HousesAdapter(houses)
        view.housesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        view.housesRecyclerView.setHasFixedSize(true)
        view.housesRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)

        // Set new house button listener
        view.newHouseBtn.setOnClickListener{
            val house: HouseDTO = HouseDTO(2, "Jones", CharacteristicsDTO(0, 0, 1, 0),
                    arrayOf(MemberDTO(1, "alice", true)))
            //TODO: get new House
            listener?.onNewHouseInteraction(house)
        }

        return view
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
