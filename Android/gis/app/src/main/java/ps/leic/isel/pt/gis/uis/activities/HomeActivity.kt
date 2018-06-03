package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*
import ps.leic.isel.pt.gis.GisApplication
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.model.UserDTO
import ps.leic.isel.pt.gis.model.dtos.*
import ps.leic.isel.pt.gis.uis.adapters.PageTabsAdapter
import ps.leic.isel.pt.gis.uis.fragments.*
import ps.leic.isel.pt.gis.utils.ExtraUtils
import ps.leic.isel.pt.gis.utils.getCurrentFragment
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
        savedInstanceState?.let {
            val fragment: Fragment = getSupportFragmentManager().getFragment(it, "FRAGMENT") ?:  HomePageFragment.newInstance()  //TODO: extrair tag
            val fragmentTag: String = fragment.tag ?: HomePageFragment.TAG
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content, fragment, fragmentTag)
                    .addToBackStack(fragmentTag)
                    .commit()
            return
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, HomePageFragment.newInstance(), HomePageFragment.TAG)
                .addToBackStack(HomePageFragment.TAG)
                .commit()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.let {
            supportFragmentManager.putFragment(it, "FRAGMENT", supportFragmentManager.getCurrentFragment()) //TODO: extrair tag
        }
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
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        //TODO: tirar alice
        index.getHousesUrl("alice")?.let {
            supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.STOCK_ITEM_LIST, StockItemListFragment.Companion::newInstance, it)
        }
    }

    // Listener for HomePageFragment
    override fun onMyHousesInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        index.getHousesUrl("alice")?.let {
            val housesUrl = it
            index.getUserUrl("alice")?.let {
                val args: Map<String, Any> = mapOf(
                        Pair(ProfileFragment.HOUSE_URL_ARG, housesUrl),
                        Pair(ProfileFragment.BASIC_INFORMATION_URL_ARG, it),
                        Pair(ProfileFragment.PAGE_ARG, PageTabsAdapter.ProfilePage.Houses)
                )
                supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.PROFILE, ProfileFragment.Companion::newInstance, args)
                return
            }
        }
        Toast.makeText(this, "This functionality is not available", Toast.LENGTH_SHORT).show() //TODO put string in xml
    }

    // Listener for HomePageFragment
    override fun onMyProfileInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        // TODO remover o alice em todo o lado
        index.getUserUrl("alice")?.let {
            val userUrl = it
            index.getHousesUrl("alice")?.let {
                val args: Map<String, Any> = mapOf(
                        Pair(ProfileFragment.BASIC_INFORMATION_URL_ARG, userUrl),
                        Pair(ProfileFragment.HOUSE_URL_ARG, it),
                        Pair(ProfileFragment.PAGE_ARG, PageTabsAdapter.ProfilePage.BasicInfo)
                )
                supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.PROFILE, ProfileFragment.Companion::newInstance, args)
                return
            }
        }
        Toast.makeText(this, "This functionality is not available", Toast.LENGTH_SHORT).show() // TODO remove string. put in xml
    }

    // Listener for HomePageFragment
    override fun onMyListsInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        index.getUserListUrl("alice")?.let {
            supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.LISTS, ListsFragment.Companion::newInstance, it)
        }
    }

    // Listener for HousesFragment interaction
    override fun onStoragesInteraction(storagesUrl: String) {
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.STORAGES, StoragesFragment.Companion::newInstance, storagesUrl)
    }

    override fun onAllergiesInteraction(allergiesUrl: String) {
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.ALLERGIES, AllergiesFragment.Companion::newInstance, allergiesUrl)
    }

    override fun onNewHouseInteraction() {
        val fragment = NewHouseDialogFragment.newInstance()
        fragment.show(supportFragmentManager, ExtraUtils.NEW_HOUSE_DIALOG)
    }

    // Listener for BasicInformationFragment interaction
    override fun onBasicInformationUpdate(user: UserDTO) {
        //TODO
    }

    // Listener for CategoriesFragement interaction
    override fun onCategoryInteraction(category: CategoryDto) {
        val args: Map<String, Any> = mapOf(
                Pair(ProductDetailFragment.URL_ARG, ""), //TODO
                Pair(CategoryProductsFragment.CATEGORY_NAME_ARG, "")   //TODO
        )
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.PRODUCTS, CategoryProductsFragment.Companion::newInstance, args)
    }

    // Listener for CategoryProductsFragement interaction
    override fun onProductInteraction(product: ProductDto) {
        val args: Map<String, Any> = mapOf(
                Pair(ProductDetailFragment.URL_ARG, ""), //TODO
                Pair(ProductDetailFragment.PRODUCT_NAME_ARG, "")   //TODO
        )
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.PRODUCT, ProductDetailFragment.Companion::newInstance, args)
    }

    // Listener for ListsFragment interaction
    override fun onListInteraction(url: String, listName: String) {
        val args: Map<String, Any> = mapOf(
                Pair(ListDetailFragment.URL_ARG, url),
                Pair(ListDetailFragment.LIST_NAME_ARG, listName)
        )
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.LIST, ListDetailFragment.Companion::newInstance, args)
    }

    // Listener for ListsFragment interaction
    override fun onNewListInteraction() {
        val fragment = NewListDialogFragment.newInstance()
        fragment.show(supportFragmentManager, ExtraUtils.NEW_LIST_DIALOG)
    }

    // Listener for ListDetailFragment interaction
    override fun onListProductInteraction(listProduct: ListProductDto) {
        //TODO: expand
        Toast.makeText(this, "Specific List-Product", Toast.LENGTH_SHORT).show()
    }

    // Listener for StockItemListFragment interaction
    override fun onStockItemInteraction(stockItem: StockItemDto) {
        val args: Map<String, Any> = mapOf(
                Pair(StockItemDetailFragment.URL_ARG, ""), //TODO
                Pair(StockItemDetailFragment.PRODUCT_NAME_ARG, ""),   //TODO
                Pair(StockItemDetailFragment.STOCK_ITEM_VARIETY_ARG, "")    //TODO
        )
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.STOCK_ITEM, StockItemDetailFragment.Companion::newInstance, args)
    }

    // Listener for StockItemListFragment interaction
    override fun onNewStockItemIteraction() {
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.WRITE_NFC_TAG, WriteNfcTagFragment.Companion::newInstance)
    }

    override fun onStorageInteraction(storage: StorageDto) {
        //TODO
        Toast.makeText(this, "Specific Storage", Toast.LENGTH_SHORT).show()
    }

    // Listener for WriteNfcTagFragment
    override fun onWriteNfcTagInteraction(tagContent: String) {
        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.NFC_MESSAGE, WritingNfcTagFragment.Companion::newInstance, tagContent)
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
            if (count == 1) {
                finish()
            } else {
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
                val url: String = ""    //TODO
                supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.INVITATIONS, InvitationsFragment.Companion::newInstance, url)
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
        val gisApplication = application as GisApplication
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_homePage -> {
                supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.HOME_PAGE, HomePageFragment.Companion::newInstance)
            }
            R.id.nav_lists -> {
                val index = gisApplication.index
                val url: String? = index.getUserListUrl("alice")//TODO: remover alice, obter username
                if (url != null) {
                    supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.LISTS, ListsFragment.Companion::newInstance, url)
                } else {
                    //TODO: error?
                }
            }
            R.id.nav_products -> {
                val index = gisApplication.index
                val url: String? = index.getCategoriesUrl()
                if (url != null) {
                    supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.CATEGORIES, CategoriesFragment.Companion::newInstance, url)
                } else {
                    //TODO: error ?
                }
            }
            R.id.nav_profile -> run {
                val index = gisApplication.index
                // TODO remover o alice em todo o lado
                index.getUserUrl("alice")?.let {
                    val userUrl = it
                    index.getHousesUrl("alice")?.let {
                        val args: Map<String, Any> = mapOf(
                                Pair(ProfileFragment.BASIC_INFORMATION_URL_ARG, userUrl),
                                Pair(ProfileFragment.HOUSE_URL_ARG, it),
                                Pair(ProfileFragment.PAGE_ARG, PageTabsAdapter.ProfilePage.BasicInfo)
                        )
                        supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.PROFILE, ProfileFragment.Companion::newInstance, args)
                        return@run
                    }
                }
                Toast.makeText(this, "This functionality is not available", Toast.LENGTH_SHORT).show() // TODO remove string. put in xml
            }
            R.id.nav_settings -> {
                supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.SETTINGS, SettingsFragment.Companion::newInstance)
            }
        }
        homeDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
