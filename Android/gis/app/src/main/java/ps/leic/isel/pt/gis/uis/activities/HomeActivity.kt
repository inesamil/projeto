package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.view_home.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ListDTO
import ps.leic.isel.pt.gis.uis.adapters.HomeListAdapter

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, HomeListAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var lists: Array<ListDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, homeDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        homeDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        homeNavView.setNavigationItemSelectedListener(this)

        // Set Adapter
        val adapter: HomeListAdapter = HomeListAdapter(lists)
        homeListsRecyclerView.layoutManager = LinearLayoutManager(this)
        homeListsRecyclerView.setHasFixedSize(true)
        homeListsRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(view: View, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_homePage -> {
                // Handle the home action
                // Nothing to do here
            }
            R.id.nav_lists -> {
                startActivity(Intent(this, ListsActivity::class.java))
            }
            R.id.nav_products -> {

            }
            R.id.nav_recipes -> {

            }
            R.id.nav_profile -> {

            }
            R.id.nav_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }

        homeDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}