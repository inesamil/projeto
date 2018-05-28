package ps.leic.isel.pt.gis.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import ps.leic.isel.pt.gis.R

fun FragmentManager.replaceCurrentFragmentWith(tag: String, newInstance: () -> Fragment) {
    var fragment = findFragmentByTag(tag)
    if (fragment == null) {
        // Fragment not present in back stack. Instantiates new fragment.
        fragment = newInstance()
        beginTransaction()
                .replace(R.id.content, fragment, tag)
                .addToBackStack(tag)
                .commit()
    } else {
        // Fragment is in the back stack
        popBackStack(tag, 0)
    }
}

fun FragmentManager.replaceCurrentFragmentWith(tag: String, newInstance: (String) -> Fragment, url: String) {
    var fragment = findFragmentByTag(tag)
    if (fragment == null) {
        // Fragment not present in back stack. Instantiates new fragment.
        fragment = newInstance(url)
        beginTransaction()
                .replace(R.id.content, fragment, tag)
                .addToBackStack(tag)
                .commit()
    } else {
        // Fragment is in the back stack
        popBackStack(tag, 0)
    }
}