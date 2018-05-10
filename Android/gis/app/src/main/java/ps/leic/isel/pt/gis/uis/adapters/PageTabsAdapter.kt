package ps.leic.isel.pt.gis.uis.adapters

import android.content.res.Resources
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.uis.fragments.BasicInformationFragment
import ps.leic.isel.pt.gis.uis.fragments.HousesFragment

class PageTabsAdapter(private val username: String, private val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    enum class ProfilePage()
    {
        BasicInfo,
        Houses
    }

    private val NUM_PAGES: Int = ProfilePage.values().size

    override fun getItem(position: Int): Fragment? {
        return when(position){
            ProfilePage.BasicInfo.ordinal -> BasicInformationFragment.newInstance(username)
            ProfilePage.Houses.ordinal -> HousesFragment.newInstance(username)
            else -> null
        }
    }

    override fun getCount() = NUM_PAGES

    override fun getPageTitle(position: Int): CharSequence? {
        //TODO
        return when(position) {
            0 -> "Basic Information"
            1 -> "Houses"
            else -> ""
        }
    }
}