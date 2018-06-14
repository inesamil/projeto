package ps.leic.isel.pt.gis.uis.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.view.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.uis.adapters.PageTabsAdapter

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnProfileFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProfileFragment : Fragment() { // TODO este fragmento precisa de fazer um pedido a api? ou recebe so o username pelo bundle?

    private lateinit var housesUrl: String
    private lateinit var userUrl: String
    private lateinit var page: PageTabsAdapter.ProfilePage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            housesUrl = it.getString(HOUSE_URL_KEY)
            userUrl = it.getString(BASIC_INFO_URL_KEY)
            page = PageTabsAdapter.ProfilePage.values()[it.getInt(PAGE_KEY)]
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Set Adapter
        val adapter = PageTabsAdapter(userUrl, housesUrl, childFragmentManager)
        view.viewPager.adapter = adapter
        view.viewPager.currentItem = page.ordinal
        view.viewPager.offscreenPageLimit = 1

        // Set TabLayout
        view.tabLayout.setupWithViewPager(view.viewPager)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            it.getString(HOUSE_URL_KEY, housesUrl)
            it.getString(BASIC_INFO_URL_KEY, userUrl)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.profile)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(HOUSE_URL_KEY, housesUrl)
        outState.putString(BASIC_INFO_URL_KEY, userUrl)
    }

    /**
     * ProfileFragment Factory
     */
    companion object {
        const val TAG: String = "ProfileFragment"
        private const val PAGE_KEY: String = "PROFILE_PAGE"
        const val PAGE_ARG: String = "page"
        private const val BASIC_INFO_URL_KEY: String = "BASIC_INFO_URL"
        const val BASIC_INFO_URL_ARG: String = "basic_information_url"
        private const val HOUSE_URL_KEY: String = "HOUSE_URL"
        const val HOUSE_URL_ARG: String = "house_url"

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
                        putString(BASIC_INFO_URL_KEY, args[BASIC_INFO_URL_ARG] as String)
                        putString(HOUSE_URL_KEY, args[HOUSE_URL_ARG] as String)
                        putInt(PAGE_KEY, (args[PAGE_ARG] as PageTabsAdapter.ProfilePage).ordinal)
                    }
                }
    }
}
