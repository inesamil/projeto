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
import android.view.View
import kotlinx.android.synthetic.main.activity_recipes.*
import kotlinx.android.synthetic.main.content_recipes.*
import kotlinx.android.synthetic.main.toolbar.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.uis.adapters.RecipesRecyclerViewAdapter

class RecipesActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        RecipesRecyclerViewAdapter.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, recipesDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        recipesDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        recipesNavView.setNavigationItemSelectedListener(this)

        val adapter = RecipesRecyclerViewAdapter(this)
        recipesRecyclerView.layoutManager = LinearLayoutManager(this)
        recipesRecyclerView.setHasFixedSize(true)
        recipesRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(view: View, position: Int) {
        Log.i("uis", "Try button clicked")
    }

    // If navigation menu is open and user click back, close the navigation bar
    override fun onBackPressed() {
        if (recipesDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            recipesDrawerLayout.closeDrawer(GravityCompat.START)
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
        return when (item.itemId) {
            R.id.invitationsItem -> true
            R.id.preferencesItem -> true
            R.id.aboutItem -> true
            else -> super.onOptionsItemSelected(item)
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
            R.id.nav_profile -> {

            }
            R.id.nav_settings -> {

            }
        }

        recipesDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
