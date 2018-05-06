package ps.leic.isel.pt.gis.uis.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_profile.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.HouseDTO
import ps.leic.isel.pt.gis.uis.adapters.PageTabsAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnProfileFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProfileFragment : Fragment() {

    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ExtraUtils.USER_USERNAME)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)

        // Set Adapter
        val adapter = PageTabsAdapter(username, childFragmentManager)
        view.viewPager.adapter = adapter

        // Set TabLayout
        view.tabLayout.setupWithViewPager(view.viewPager)

        return view
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.profile)
    }

    /**
     * ProfileFragment Factory
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param username Username
         * @return A new instance of fragment ProfileFragment.
         */
        @JvmStatic
        fun newInstance(username: String) =
                ProfileFragment().apply {
                    arguments = Bundle().apply {
                       putString(ExtraUtils.USER_USERNAME, username)
                    }
                }
    }
}
