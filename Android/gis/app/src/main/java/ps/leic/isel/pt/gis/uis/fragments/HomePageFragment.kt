package ps.leic.isel.pt.gis.uis.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.view.*

import ps.leic.isel.pt.gis.R

/**
 * A simple [Fragment] subclass.
 * Use the [HomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomePageFragment : Fragment() {

    private var listener: OnHomeFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHomeFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnHomeFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Set on my pantry click listener
        view.myPantriesCardView.setOnClickListener {
            listener?.onMyPantryInteraction()
        }

        // Set on my houses click listener
        view.myHousesCardView.setOnClickListener {
            listener?.onMyHousesInteraction()
        }

        // Set on my profile click listener
        view.myProfileCardView.setOnClickListener {
            listener?.onMyProfileInteraction()
        }

        // Set on my lists click listener
        view.myListsCardView.setOnClickListener {
            listener?.onMyListsInteraction()
        }

        // Set on tags click listener
        view.myTagsCardView.setOnClickListener {
            listener?.onMyTagsInteraction()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.homePage)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /***
     * Listeners
     ***/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnHomeFragmentInteractionListener {
        fun onMyPantryInteraction()
        fun onMyHousesInteraction()
        fun onMyProfileInteraction()
        fun onMyListsInteraction()
        fun onMyTagsInteraction()
    }

    /**
     * HousesFragment Factory
     */
    companion object {
        const val TAG: String = "HomePageFrgament"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomePageFragment.
         */
        @JvmStatic
        fun newInstance() = HomePageFragment()
    }
}
