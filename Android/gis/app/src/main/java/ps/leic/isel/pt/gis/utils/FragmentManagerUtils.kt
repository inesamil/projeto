package ps.leic.isel.pt.gis.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import ps.leic.isel.pt.gis.R

fun FragmentManager.getCurrentFragment(): Fragment {
    return findFragmentById(R.id.content)
}

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

fun FragmentManager.replaceCurrentFragmentWith(tag: String, newInstance: (args: Map<String, Any>) -> Fragment, args: Map<String, Any>) {
    var fragment = findFragmentByTag(tag)
    if (fragment == null) {
        // Fragment not present in back stack. Instantiates new fragment.
        fragment = newInstance(args)
        beginTransaction()
                .replace(R.id.content, fragment, tag)
                .addToBackStack(tag)
                .commit()
    } else {
        // Fragment is in the back stack
        popBackStack(tag, 0)
    }
}

fun FragmentManager.removeFragmentByTag(tag: String) {
    val fragment = findFragmentByTag(tag)
    if (fragment != null) {
        beginTransaction()
                .remove(fragment)
                .commit()
    }
}

fun FragmentManager.removeFragment(fragment: Fragment) {
    beginTransaction()
            .remove(fragment)
            .commit()

}