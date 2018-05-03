package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.R.id.categoryDrawerLayout
import ps.leic.isel.pt.gis.model.CategoryDTO
import ps.leic.isel.pt.gis.uis.adapters.CategoriesAdapter

class CategoriesActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, CategoriesAdapter.OnItemClickListener {

    private lateinit var categories: Array<CategoryDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, categoryDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        categoryDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        //
        categoryNavView.setNavigationItemSelectedListener(this)

        categories = arrayOf(
                CategoryDTO(1, "Laticínios"),
                CategoryDTO(2, "Carne"),
                CategoryDTO(3, "Peixe"))

        // Set Adapter
        val adapter = CategoriesAdapter(categories)
        categoryRecyclerView.setHasFixedSize(true)
        categoryRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(view: View, position: Int) {
        //TODO: get products or not
        startActivity(Intent(this, CategoryProductsActivity::class.java))
    }

    // If navigation menu is open and user click back, close the navigation bar
    override fun onBackPressed() {
        if (categoryDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            categoryDrawerLayout.closeDrawer(GravityCompat.START)
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
        val success = ToolbarMenuHelper.handleOnItemSelected(item)
        return if (success) true else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_homePage -> {
                // Handle the home action
            }
            R.id.nav_lists -> {
                startActivity(Intent(this, StockItemListActivity::class.java))
            }
            R.id.nav_products -> {
                startActivity(Intent(this, StockItemDetailsActivity::class.java))
            }
            R.id.nav_recipes -> {
                startActivity(Intent(this, RecipesActivity::class.java))
            }
            R.id.nav_profile -> {

            }
            R.id.nav_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }

        categoryDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}