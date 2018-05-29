package ps.leic.isel.pt.gis.uis.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.view.*
import ps.leic.isel.pt.gis.R
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
    private lateinit var page: PageTabsAdapter.ProfilePage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ExtraUtils.USER_USERNAME)
            page = PageTabsAdapter.ProfilePage.values()[it.getInt(ExtraUtils.PROFILE_PAGE)]
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Set Adapter
        val adapter = PageTabsAdapter(username, childFragmentManager)
        view.viewPager.adapter = adapter
        view.viewPager.currentItem = page.ordinal

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

        const val URL_ARG: String = "url"
        const val PAGE_ARG: String = "page"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param args Arguments
         * @return A new instance of fragment ProfileFragment.
         */
        @JvmStatic
        fun newInstance(args: Map<String, Any>) =
                ProfileFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExtraUtils.URL, args[URL_ARG] as String)
                        putInt(ExtraUtils.PROFILE_PAGE, (args[PAGE_ARG] as PageTabsAdapter.ProfilePage).ordinal)
                    }
                }
    }
}
