package ps.leic.isel.pt.gis.uis.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.view_list.*
import kotlinx.android.synthetic.main.toolbar.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListProductDTO
import ps.leic.isel.pt.gis.uis.adapters.ListAdapter

class ListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var listProducts: Array<ListProductDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, listDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        listDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        listNavView.setNavigationItemSelectedListener(this)

        val adapter = ListAdapter(listProducts)
        listRecyclerView.layoutManager = LinearLayoutManager(this)
        listRecyclerView.setHasFixedSize(true)
        listRecyclerView.adapter = adapter

        listConstraintLayout.setOnClickListener {
            Log.i("uis", "Transfer to offline clicked.")
        }
    }

    // If navigation menu is open and user click back, close the navigation bar
    override fun onBackPressed() {
        if (listDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            listDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.right_menu_with_search, menu)
        return true
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
                // Handle the home action
            }
            R.id.nav_lists -> {

            }
            R.id.nav_products -> {

            }
            R.id.nav_recipes -> {

            }
            R.id.nav_profile -> {

            }
            R.id.nav_settings -> {

            }
        }

        listDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
