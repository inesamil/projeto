package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_nfc.*
import kotlinx.android.synthetic.main.toolbar.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.*
import ps.leic.isel.pt.gis.uis.fragments.*
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.replaceCurrentFragmentWith

class HomeActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        SettingsFragment.OnSettingsFragmentInteractionListener,
        CategoriesFragment.OnCategoriesFragmentInteractionListener,
        CategoryProductsFragment.OnCategoryProductsFragmentInteractionListener,
        ListsFragment.OnListsFragmentInteractionListener,
        ListDetailFragment.OnListDetailFragmentInteractionListener,
        StockItemListFragment.OnStockItemListFragmentInteractionListener,
        StockItemDetailFragment.OnStockItemDetailFragmentInteractionListener,
        WriteNfcTagFragment.OnWriteNfcTagFragmentInteractionListener {

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

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        supportActionBar?.title = title
    }

    /**
     * Fragment Listeners
     */

    // Listener for CategoriesFragement interaction
    override fun onCategoryInteraction(category: CategoryDTO) {
        val fragment = CategoryProductsFragment.newInstance(category)
        supportFragmentManager.replaceCurrentFragmentWith(fragment, ExtraUtils.PRODUCTS)
    }

    // Listener for CategoryProductsFragement interaction
    override fun onProductInteraction(product: ProductDTO) {
        val fragment = ProductDetailFragment.newInstance(product)
        supportFragmentManager.replaceCurrentFragmentWith(fragment, ExtraUtils.PRODUCT)
    }

    // Listener for ListsFragment interaction
    override fun onListInteraction(list: ListDTO) {
        val fragment = ListDetailFragment.newInstance(list)
        supportFragmentManager.replaceCurrentFragmentWith(fragment, ExtraUtils.LIST)
    }

    // Listener for ListDetailFragment interaction
    override fun onListProductInteraction(listProductDTO: ListProductDTO) {
        //TODO: expand
        Toast.makeText(this, "Specific List-Product", Toast.LENGTH_SHORT).show()
    }

    // Listener for StockItemListFragment interaction
    override fun onStockItemInteraction(stockItem: StockItemDTO) {
        val fragment = StockItemDetailFragment.newInstance(stockItem)
        supportFragmentManager.replaceCurrentFragmentWith(fragment, ExtraUtils.STOCK_ITEM)
    }

    override fun onStorageInteraction(storage: StorageDTO) {
        //TODO
        Toast.makeText(this, "Specific Storage", Toast.LENGTH_SHORT).show()
    }

    // Listener for WriteNfcTagFragment
    override fun onWriteNfcTagInteraction(tagContent: String) {
        val fragment = WritingNfcTagFragment.newInstance(tagContent)
        supportFragmentManager.replaceCurrentFragmentWith(fragment, ExtraUtils.NFC_MESSAGE)
    }

    // Listener for new intents (NFC tag intents)
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val tag = intent?.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show()
            val messageToWrite = msgTxt.text.toString()
            val writingNfcTagFragment = supportFragmentManager.findFragmentByTag(ExtraUtils.WRITING_NFC_TAG) as? WritingNfcTagFragment
            writingNfcTagFragment?.let {
                if (it.isVisible)
                    it.onNfcDetected(intent)
            }
        }
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
            R.id.invitationsItem -> {
                replaceFragment(ExtraUtils.WRITE_NFC_TAG, WriteNfcTagFragment.Companion::newInstance)
                return true
            }
            R.id.preferencesItem -> return true
            R.id.aboutItem -> {
                replaceFragment(ExtraUtils.ABOUT, AboutFragment.Companion::newInstance)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_homePage -> {
                val fragment = StockItemListFragment.newInstance(1)
                supportFragmentManager.replaceCurrentFragmentWith(fragment, ExtraUtils.STOCK_ITEM)
            }
            R.id.nav_lists -> {
                replaceFragment(ExtraUtils.LISTS, ListsFragment.Companion::newInstance)
            }
            R.id.nav_products -> {
                replaceFragment(ExtraUtils.CATEGORIES, CategoriesFragment.Companion::newInstance)
            }
            R.id.nav_profile -> {
                val fragment = AllergiesFragment.newInstance(1, false)
                supportFragmentManager.replaceCurrentFragmentWith(fragment, ExtraUtils.ALLERGIES)
            }
            R.id.nav_settings -> {
                replaceFragment(ExtraUtils.SETTINGS, SettingsFragment.Companion::newInstance)
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

