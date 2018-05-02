package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_category_products.*
import kotlinx.android.synthetic.main.content_product_diary.*
import kotlinx.android.synthetic.main.toolbar.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.ProductDTO
import ps.leic.isel.pt.gis.uis.adapters.CategoryProductsAdapter
import ps.leic.isel.pt.gis.utils.ExtraUtils

class CategoryProductsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, CategoryProductsAdapter.OnItemClickListener {

    private lateinit var products: Array<ProductDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_products)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, diaryDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        diaryDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        diaryNavView.setNavigationItemSelectedListener(this)

        val diaries = resources.getStringArray(R.array.products_diaries)

        // Set Adapter
        val adapter = CategoryProductsAdapter(this, diaries)
        diaryRecyclerView.layoutManager = LinearLayoutManager(this)
        diaryRecyclerView.setHasFixedSize(true)
        diaryRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
    }

    override fun onTextClick(view: View, position: Int) {
        val intent: Intent = Intent(this, ProductActivity::class.java)
        intent.putExtra(ExtraUtils.PRODUCT, products[position])
        startActivity(intent)
    }

    override fun onPlusClick(view: View, position: Int) {
        //TODO
        Log.i("uis", "plus click on position: $position")
    }

    override fun onMinusClick(view: View, position: Int) {
        //TODO
        Log.i("uis", "minus click on position: $position")
    }

    // If navigation menu is open and user click back, close the navigation bar
    override fun onBackPressed() {
        if (diaryDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            diaryDrawerLayout.closeDrawer(GravityCompat.START)
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

            }
        }

        diaryDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
