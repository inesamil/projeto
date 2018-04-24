package ps.leic.isel.pt.gis

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_stock_item_list.*
import kotlinx.android.synthetic.main.content_stock_item_list.*
import kotlinx.android.synthetic.main.toolbar.*

class StockItemListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_item_list)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, stockItemListDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        stockItemListDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        stockItemListNavView.setNavigationItemSelectedListener(this)

        // title = "Milk" // Set the activity label here

        val dummy = arrayOf("Mimosa", "Terra Nostra")
        val adapter = StockItemListRecyclerViewAdapter(this, dummy)
        stockItemListRecyclerView.layoutManager = LinearLayoutManager(this)
        stockItemListRecyclerView.setHasFixedSize(true)
        stockItemListRecyclerView.adapter = adapter
    }

    // If navigation menu is open and user click back, close the navigation bar
    override fun onBackPressed() {
        if (stockItemListDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            stockItemListDrawerLayout.closeDrawer(GravityCompat.START)
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
            R.id.app_bar_search -> return true
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
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }

        stockItemListDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}