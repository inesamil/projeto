package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListDTO
import ps.leic.isel.pt.gis.model.ids.CategoryID
import ps.leic.isel.pt.gis.model.ids.ProductID
import ps.leic.isel.pt.gis.uis.fragments.CategoriesFragment
import ps.leic.isel.pt.gis.uis.fragments.CategoryProductsFragment
import ps.leic.isel.pt.gis.uis.fragments.ListsFragment
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.replaceCurrentFragmentWith

class HomeActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        CategoriesFragment.OnCategoriesFragmentInteractionListener,
        CategoryProductsFragment.OnCategoryProductsFragmentInteractionListener,
        ListsFragment.OnListsFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, homeDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        homeDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        homeNavView.setNavigationItemSelectedListener(this)

        // Init
        supportFragmentManager.beginTransaction()
                            .replace(R.id.content, CategoriesFragment.newInstance(), ExtraUtils.CATEGORIES)
                            .addToBackStack(ExtraUtils.CATEGORIES)
                            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.right_menu_with_search, menu)
        return true
    }

    /**
     * Fragment Listeners
     */

    // Listener for CategoriesFragement interaction
    override fun onCategoryInteraction(categoryId: CategoryID, categoryName: String) {
        val fragment = CategoryProductsFragment.newInstance(categoryId, categoryName)
        supportFragmentManager.replaceCurrentFragmentWith(fragment, ExtraUtils.PRODUCTS)
    }

    // Listener for CategoryProductsFragement interaction
    override fun onProductInteraction(productId: ProductID) {
        //TODO
        Toast.makeText(this, "Specific product fragement", Toast.LENGTH_SHORT).show()
    }

    override fun onListInteraction(listId: ListDTO) {
        //TODO
        Toast.makeText(this, "Specific List", Toast.LENGTH_SHORT).show()
    }

    /**
     * Navigation Listeners
     */

    // If navigation menu is open and user click back, close the navigation bar
    // Otherwise go back in the fragment stack
    override fun onBackPressed() {
        if (homeDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            homeDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
           val count: Int = supportFragmentManager.backStackEntryCount
            if (count == 1){
                finish()
            }
            else {
                supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.invitationsItem -> return true
            R.id.preferencesItem -> return true
            R.id.aboutItem -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_homePage -> {
                //replaceFragment(ExtraUtils.HOME, )
            }
            R.id.nav_lists -> {
                replaceFragment(ExtraUtils.LISTS, ListsFragment.Companion::newInstance)
            }
            R.id.nav_products -> {
                replaceFragment(ExtraUtils.CATEGORIES, CategoriesFragment.Companion::newInstance)
            }
            R.id.nav_profile -> {
                //TODO
            }
            R.id.nav_settings -> {
                //TODO
            }
        }
        homeDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun replaceFragment(tag: String, newInstance: () -> Fragment) {
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null){
            // Fragment not present in back stack. Instantiates new fragment.
            fragment = newInstance()
            supportFragmentManager.replaceCurrentFragmentWith(fragment, tag)
        }
        else {
            // Fragment is in the back stack
            supportFragmentManager.popBackStack(tag, 0)
        }
    }

}

