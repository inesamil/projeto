package ps.leic.isel.pt.gis.uis.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ps.leic.isel.pt.gis.uis.fragments.BasicInformationFragment
import ps.leic.isel.pt.gis.uis.fragments.HousesFragment

class PageTabsAdapter(private val userUrl: String, private val houseUrl: String, fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager) {

    enum class ProfilePage {
        BasicInfo,
        Houses
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            ProfilePage.BasicInfo.ordinal -> BasicInformationFragment.newInstance(userUrl)
            ProfilePage.Houses.ordinal -> HousesFragment.newInstance(houseUrl)
            else -> null
        }
    }

    override fun getCount() = ProfilePage.values().size

    override fun getPageTitle(position: Int): CharSequence? {
        //TODO passar contexto ao adapter e ir ao strings.xml buscar os titulos
        return when (position) {
            0 -> "Basic Information"
            1 -> "Houses"
            else -> ""
        }
    }
}