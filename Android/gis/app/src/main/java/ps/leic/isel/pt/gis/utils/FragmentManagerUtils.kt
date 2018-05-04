package ps.leic.isel.pt.gis.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import ps.leic.isel.pt.gis.R

fun FragmentManager.replaceCurrentFragmentWith(fragment: Fragment, tag: String) {
    beginTransaction()
            .replace(R.id.content, fragment, tag)
            .addToBackStack(tag)
            .commit()
}