package com.example.nunoveloso.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_product_category.*
import kotlinx.android.synthetic.main.content_product_category.*
import kotlinx.android.synthetic.main.toolbar.*

class ProductCategoryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ProductCategoryRecyclerViewAdapter.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_category)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, categoryDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        categoryDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        categoryNavView.setNavigationItemSelectedListener(this)

        val categories = resources.getStringArray(R.array.products_categories)
        val adapter = ProductCategoryRecyclerViewAdapter(this, categories)
        categoryRecyclerView.setHasFixedSize(true)
        categoryRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(view: View, position: Int) {
        startActivity(Intent(this, ProductDiaryActivity::class.java))
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