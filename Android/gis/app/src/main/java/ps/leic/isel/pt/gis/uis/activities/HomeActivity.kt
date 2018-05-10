package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.model.*
import ps.leic.isel.pt.gis.uis.adapters.PageTabsAdapter
import ps.leic.isel.pt.gis.uis.fragments.*
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.replaceCurrentFragmentWith

class HomeActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        SettingsFragment.OnSettingsFragmentInteractionListener,
        HomePageFragment.OnHomeFragmentInteractionListener,
        HousesFragment.OnHousesFragmentInteractionListener,
        BasicInformationFragment.OnBasicInformationFragmentInteractionListener,
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
    // Listener for HomePageFragment
    override fun onMyPantryInteraction() {
        val args: Map<String, Any> = mutableMapOf(
                Pair(StockItemListFragment.usernameArg, "alice")    //TODO
        )
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.STOCK_ITEM_LIST, StockItemListFragment.Companion::newInstance, args)
    }

    // Listener for HomePageFragment
    override fun onMyHousesInteraction() {
        //TODO
    }

    // Listener for HomePageFragment
    override fun onMyProfileInteraction() {
        //TODO
    }

    // Listener for HomePageFragment
    override fun onMyListsInteraction() {
        //TODO
    }

    // Listener for HousesFragment interaction
    override fun onStoragesInteraction(houseId: Long) {
        val args: Map<String, Any> = mutableMapOf(
                Pair(StoragesFragment.houseIdArg, houseId)
        )
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.STORAGES, StoragesFragment.Companion::newInstance, args)
    }

    override fun onAllergiesInteraction(houseId: Long) {
        val args: Map<String, Any> = mutableMapOf(
                Pair(AllergiesFragment.houseIdArg, houseId),
                Pair(AllergiesFragment.showAllergiesArg, true) //TODO: get preferences, show allergies or not
        )
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.ALLERGIES, AllergiesFragment.Companion::newInstance, args)
    }

    override fun onNewHouseInteraction(house: HouseDTO) {
        Toast.makeText(this, "New house added", Toast.LENGTH_SHORT).show()
        //TODO
    }

    // Listener for BasicInformationFragment interaction
    override fun onBasicInformationUpdate(user: UserDTO) {
        //TODO
    }

    // Listener for CategoriesFragement interaction
    override fun onCategoryInteraction(category: CategoryDTO) {
        val args: Map<String, Any> = mutableMapOf(
                Pair(CategoryProductsFragment.categoryArg, category)
        )
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.PRODUCTS, CategoryProductsFragment.Companion::newInstance, args)
    }

    // Listener for CategoryProductsFragement interaction
    override fun onProductInteraction(product: ProductDTO) {
        val args: Map<String, Any> = mutableMapOf(
                Pair(ProductDetailFragment.productArg, product)
        )
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.PRODUCT, ProductDetailFragment.Companion::newInstance, args)
    }

    // Listener for ListsFragment interaction
    override fun onListInteraction(list: ListDTO) {
        val args: Map<String, Any> = mutableMapOf(
                Pair(ListDetailFragment.listArg, list)
        )
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.LIST, ListDetailFragment.Companion::newInstance, args)
    }

    // Listener for ListDetailFragment interaction
    override fun onListProductInteraction(listProductDTO: ListProductDTO) {
        //TODO: expand
        Toast.makeText(this, "Specific List-Product", Toast.LENGTH_SHORT).show()
    }

    // Listener for StockItemListFragment interaction
    override fun onStockItemInteraction(stockItem: StockItemDTO) {
        val args: Map<String, Any> = mutableMapOf(
                Pair(StockItemDetailFragment.stockItemArg, stockItem)
        )
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.STOCK_ITEM, StockItemDetailFragment.Companion::newInstance, args)
    }

    // Listener for StockItemListFragment interaction
    override fun onNewStockItemIteraction() {
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.WRITE_NFC_TAG, WriteNfcTagFragment.Companion::newInstance)
    }

    override fun onStorageInteraction(storage: StorageDTO) {
        //TODO
        Toast.makeText(this, "Specific Storage", Toast.LENGTH_SHORT).show()
    }

    // Listener for WriteNfcTagFragment
    override fun onWriteNfcTagInteraction(tagContent: String) {
        val args: Map<String, Any> = mutableMapOf(
                Pair(WritingNfcTagFragment.messageArg, tagContent)
        )
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.NFC_MESSAGE, WritingNfcTagFragment.Companion::newInstance, args)
    }

    // Listener for new intents (NFC tag intents)
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val tag = intent?.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show()
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
                val args: Map<String, Any> = mutableMapOf(
                        Pair(InvitationsFragment.usernameArg, "alice") //TODO
                )
                supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.INVITATIONS, InvitationsFragment.Companion::newInstance, args)
                return true
            }
            R.id.preferencesItem -> {
                //TODO
                return true
            }
            R.id.aboutItem -> {
                supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.ABOUT, AboutFragment.Companion::newInstance)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_homePage -> {
                supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.HOME_PAGE, HomePageFragment.Companion::newInstance)
            }
            R.id.nav_lists -> {
                supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.LISTS, ListsFragment.Companion::newInstance)
            }
            R.id.nav_products -> {
                supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.CATEGORIES, CategoriesFragment.Companion::newInstance)
            }
            R.id.nav_profile -> {
                val args: Map<String, Any> = mutableMapOf(
                        Pair(ProfileFragment.usernameArg, "alice"), //TODO
                        Pair(ProfileFragment.pageArg, PageTabsAdapter.ProfilePage.BasicInfo)
                )
                supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.PROFILE, ProfileFragment.Companion::newInstance, args)
            }
            R.id.nav_settings -> {
                supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.SETTINGS, SettingsFragment.Companion::newInstance)
            }
        }
        homeDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}

