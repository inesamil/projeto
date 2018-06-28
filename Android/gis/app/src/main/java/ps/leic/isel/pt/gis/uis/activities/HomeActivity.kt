package ps.leic.isel.pt.gis.uis.activities

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*
import ps.leic.isel.pt.gis.GisApplication
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.House
import ps.leic.isel.pt.gis.model.List
import ps.leic.isel.pt.gis.model.ListProduct
import ps.leic.isel.pt.gis.model.UserDTO
import ps.leic.isel.pt.gis.model.dtos.*
import ps.leic.isel.pt.gis.repositories.Resource
import ps.leic.isel.pt.gis.repositories.Status
import ps.leic.isel.pt.gis.uis.adapters.PageTabsAdapter
import ps.leic.isel.pt.gis.uis.fragments.*
import ps.leic.isel.pt.gis.utils.getCurrentFragment
import ps.leic.isel.pt.gis.utils.replaceCurrentFragmentWith
import ps.leic.isel.pt.gis.viewModel.ListDetailViewModel

class HomeActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        SettingsFragment.OnSettingsFragmentInteractionListener,
        HomePageFragment.OnHomeFragmentInteractionListener,
        HousesFragment.OnHousesFragmentInteractionListener,
        NewHouseDialogFragment.OnNewHouseDialogFragmentInteractionListener,
        BasicInformationFragment.OnBasicInformationFragmentInteractionListener,
        CategoriesFragment.OnCategoriesFragmentInteractionListener,
        ListsFragment.OnListsFragmentInteractionListener,
        NewListDialogFragment.OnNewListDialogFragmentInteractionListener,
        ListDetailFragment.OnListDetailFragmentInteractionListener,
        AddProductToListDialogFragment.OnAddProductToListDialogFragmentInteractionListener,
        EditListProductDialogFragment.OnEditListProductDialogFragmentInteractionListener,
        StockItemListFragment.OnStockItemListFragmentInteractionListener,
        StockItemDetailFragment.OnStockItemDetailFragmentInteractionListener,
        ListsFiltersDialogFragment.OnListsFiltersDialogFragmentInteractionListener {

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
            var tag = it.getString("FRAGMENT")
            var fragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)
            if (fragment == null) {
                fragment = HomePageFragment.newInstance()
                tag = HomePageFragment.TAG
            }
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content, fragment, tag)
                    .addToBackStack(tag)
                    .commit()
            return
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, HomePageFragment.newInstance(), HomePageFragment.TAG)
                .addToBackStack(HomePageFragment.TAG)
                .commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("FRAGMENT", supportFragmentManager.getCurrentFragment().tag)
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

    fun goBackInStack() {

    }

    /**
     * Fragment Listeners
     */
    // Listener for HomePageFragment
    override fun onMyPantryInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        val username = ServiceLocator.getCredentialsStore(applicationContext).getUsername()
        if (username == null) {
            Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
            //TODO: logout
            return
        }
        index.getHousesUrl(username)?.let {
            supportFragmentManager.replaceCurrentFragmentWith(StockItemListFragment.TAG, StockItemListFragment.Companion::newInstance, it)
        }
    }

    // Listener for HomePageFragment
    override fun onMyHousesInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        val username = ServiceLocator.getCredentialsStore(applicationContext).getUsername()
        if (username == null) {
            Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
            //TODO: logout
            return
        }
        index.getHousesUrl(username)?.let {
            val housesUrl = it
            index.getUserUrl(username)?.let {
                val args: Map<String, Any> = mapOf(
                        Pair(ProfileFragment.HOUSE_URL_ARG, housesUrl),
                        Pair(ProfileFragment.BASIC_INFO_URL_ARG, it),
                        Pair(ProfileFragment.PAGE_ARG, PageTabsAdapter.ProfilePage.Houses)
                )
                supportFragmentManager.replaceCurrentFragmentWith(ProfileFragment.TAG, ProfileFragment.Companion::newInstance, args)
                return
            }
        }
        Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
    }

    // Listener for HomePageFragment
    override fun onMyProfileInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        val username = ServiceLocator.getCredentialsStore(applicationContext).getUsername()
        if (username == null) {
            Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
            //TODO: logout
            return
        }
        index.getUserUrl(username)?.let {
            val userUrl = it
            index.getHousesUrl(username)?.let {
                val args: Map<String, Any> = mapOf(
                        Pair(ProfileFragment.BASIC_INFO_URL_ARG, userUrl),
                        Pair(ProfileFragment.HOUSE_URL_ARG, it),
                        Pair(ProfileFragment.PAGE_ARG, PageTabsAdapter.ProfilePage.BasicInfo)
                )
                supportFragmentManager.replaceCurrentFragmentWith(ProfileFragment.TAG, ProfileFragment.Companion::newInstance, args)
                return
            }
        }
        Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
    }

    // Listener for HomePageFragment
    override fun onMyListsInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        val username = ServiceLocator.getCredentialsStore(applicationContext).getUsername()
        if (username == null) {
            Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
            //TODO: logout
            return
        }
        index.getUserListUrl(username)?.let {
            supportFragmentManager.replaceCurrentFragmentWith(ListsFragment.TAG, ListsFragment.Companion::newInstance, it)
        }
    }

    // Listener for HomPageFragment interaction
    override fun onMyTagsInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        val url: String? = index.getCategoriesUrl()
        url?.let {
            supportFragmentManager.replaceCurrentFragmentWith(WriteNfcTagFragment.TAG, WriteNfcTagFragment.Companion::newInstance, it)
            return
        }
        Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
    }

    // Listener for HousesFragment interaction
    override fun onStoragesInteraction(storagesUrl: String) {
        supportFragmentManager.replaceCurrentFragmentWith(StoragesFragment.TAG, StoragesFragment.Companion::newInstance, storagesUrl)
    }

    override fun onAllergiesInteraction(allergiesUrl: String) {
        supportFragmentManager.replaceCurrentFragmentWith(AllergiesFragment.TAG, AllergiesFragment.Companion::newInstance, allergiesUrl)
    }

    override fun onNewHouseInteraction() {
        val fragment = NewHouseDialogFragment.newInstance()
        fragment.show(supportFragmentManager, NewHouseDialogFragment.TAG)
    }

    override fun onAddHouse(house: House) {
        val fragment = supportFragmentManager.findFragmentByTag(HousesFragment.TAG) as? HousesFragment
        fragment?.onAddHouse(house)
    }

    // Listener for BasicInformationFragment interaction
    override fun onBasicInformationUpdate(user: UserDTO) {
        Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
        // TODO atualizar info do user
    }

    // Listener for CategoriesFragement interaction
    override fun onCategoryInteraction(url: String, categoryName: String) {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        val username = ServiceLocator.getCredentialsStore(applicationContext).getUsername()
        if (username == null) {
            Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
            //TODO: logout
            return
        }
        index.getUserListUrl(username)?.let {
            val args: Map<String, Any> = mapOf(
                    Pair(CategoryProductsFragment.URL_ARG, url),
                    Pair(CategoryProductsFragment.CATEGORY_NAME_ARG, categoryName)
            )
            supportFragmentManager.replaceCurrentFragmentWith(CategoryProductsFragment.TAG, CategoryProductsFragment.Companion::newInstance, args)
        }
    }

    // Listener for CategoryProductsFragement interaction
    override fun onAddProductToListInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        val username = ServiceLocator.getCredentialsStore(applicationContext).getUsername()
        if (username == null) {
            Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
            //TODO: logout
            return
        }
        index.getCategoriesUrl()?.let {
            val dialog = AddProductToListDialogFragment.newInstance(it)
            dialog.show(supportFragmentManager, AddProductToListDialogFragment.TAG)
        }
    }

    override fun onEditListProductInteraction(listProduct: ListProductDto) {
        val dialog = EditListProductDialogFragment.newInstance(listProduct.productId, listProduct.productName, listProduct.productsListQuantity)
        dialog.show(supportFragmentManager, EditListProductDialogFragment.TAG)
    }

    override fun onDeleteListProductInteraction(listProduct: ListProductDto) {
        val dialog = DeleteConfirmationDialogFragment.newInstance()
        dialog.setOnDeleteConfirmationDialogListener(object : DeleteConfirmationDialogFragment.OnDeleteConfirmationDialogFragmentListener {
            override fun onPositiveConfirmation() {
                val fragment = supportFragmentManager.findFragmentByTag(ListDetailFragment.TAG) as? ListDetailFragment
                fragment?.onListProductRemoved(listProduct)
            }
        })
        dialog.show(supportFragmentManager, DeleteConfirmationDialogFragment.TAG)
    }

    override fun onAddProductToList(listProduct: ListProduct) {
        val fragment = supportFragmentManager.findFragmentByTag(ListDetailFragment.TAG) as? ListDetailFragment
        fragment?.onAddProductToList(listProduct)
    }

    override fun onEditListProduct(listProduct: ListProduct) {
        val fragment = supportFragmentManager.findFragmentByTag(ListDetailFragment.TAG) as? ListDetailFragment
        fragment?.onListProductEdited(listProduct)
    }

    // Listener for ListsFragment interaction
    override fun onListInteraction(url: String, listName: String) {
        val args: Map<String, Any> = mapOf(
                Pair(ListDetailFragment.URL_ARG, url),
                Pair(ListDetailFragment.LIST_NAME_ARG, listName)
        )
        supportFragmentManager.replaceCurrentFragmentWith(ListDetailFragment.TAG, ListDetailFragment.Companion::newInstance, args)
    }

    // Listener for ListsFragment interaction
    override fun onNewListInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        val username = ServiceLocator.getCredentialsStore(applicationContext).getUsername()
        if (username == null) {
            Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
            //TODO: logout
            return
        }
        index.getHousesUrl(username)?.let {
            val fragment = NewListDialogFragment.newInstance(it)
            fragment.show(supportFragmentManager, NewListDialogFragment.TAG)
        }
    }

    override fun onAddList(list: List) {
        val fragment = supportFragmentManager.findFragmentByTag(ListsFragment.TAG) as? ListsFragment
        fragment?.onAddList(list)
    }

    // Listener for ListsFragment interaction
    override fun onFiltersInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        val username = ServiceLocator.getCredentialsStore(applicationContext).getUsername()
        if (username == null) {
            Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
            //TODO: logout
            return
        }
        index.getHousesUrl(username)?.let {
            val fragment = ListsFiltersDialogFragment.newInstance(it)
            fragment.show(supportFragmentManager, ListsFiltersDialogFragment.TAG)
        }
    }

    // Listener for ListDetailFragment interaction
    override fun onListDownload() {
        //TODO: download
        Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_LONG).show()
    }

    // Listener for ListsFiltersDialogFragment interaction
    override fun onFiltersApply(systemLists: Boolean, userLists: Boolean, sharedLists: Boolean, houses: Array<HouseDto>?) {
        val listsFragment = supportFragmentManager.findFragmentByTag(ListsFragment.TAG) as? ListsFragment
        listsFragment?.onFiltersApplied(systemLists, userLists, sharedLists, houses)
    }

    // Listener for StockItemListFragment interaction
    override fun onStockItemInteraction(url: String, productName: String, stockItemVariety: String) {
        val args: Map<String, Any> = mapOf(
                Pair(StockItemDetailFragment.URL_ARG, url),
                Pair(StockItemDetailFragment.PRODUCT_NAME_ARG, productName),
                Pair(StockItemDetailFragment.STOCK_ITEM_VARIETY_ARG, stockItemVariety)
        )
        supportFragmentManager.replaceCurrentFragmentWith(StockItemDetailFragment.TAG, StockItemDetailFragment.Companion::newInstance, args)
    }

    override fun onStorageInteraction(storage: StorageDto) {
        //TODO
        Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
    }

    // Listener for new intents (NFC tag intents)
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val tag = intent?.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        tag?.let {
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show()
            val writeNfcTagFragment = supportFragmentManager.findFragmentByTag(WriteNfcTagFragment.TAG) as? WriteNfcTagFragment
            writeNfcTagFragment?.let {
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
                // supportFragmentManager.replaceCurrentFragmentWith(ExtraUtils.INVITATIONS, InvitationsFragment.Companion::newInstance, url)
                Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.aboutItem -> {
                supportFragmentManager.replaceCurrentFragmentWith(AboutFragment.TAG, AboutFragment.Companion::newInstance)
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
                supportFragmentManager.replaceCurrentFragmentWith(HomePageFragment.TAG, HomePageFragment.Companion::newInstance)
            }
            R.id.nav_lists -> run {
                val index = gisApplication.index
                val username = ServiceLocator.getCredentialsStore(applicationContext).getUsername()
                if (username == null) {
                    Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
                    //TODO: logout
                    return true
                }
                val url: String? = index.getUserListUrl(username)
                url?.let {
                    supportFragmentManager.replaceCurrentFragmentWith(ListsFragment.TAG, ListsFragment.Companion::newInstance, url)
                    return@run
                }
                Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
            }
            R.id.nav_products -> run {
                val index = gisApplication.index
                val url: String? = index.getCategoriesUrl()
                url?.let {
                    supportFragmentManager.replaceCurrentFragmentWith(CategoriesFragment.TAG, CategoriesFragment.Companion::newInstance, url)
                    return@run
                }
                Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
            }
            R.id.nav_profile -> run {
                val index = gisApplication.index
                val username = ServiceLocator.getCredentialsStore(applicationContext).getUsername()
                if (username == null) {
                    Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
                    //TODO: logout
                    return true
                }
                index.getUserUrl(username)?.let {
                    val userUrl = it
                    index.getHousesUrl(username)?.let {
                        val args: Map<String, Any> = mapOf(
                                Pair(ProfileFragment.BASIC_INFO_URL_ARG, userUrl),
                                Pair(ProfileFragment.HOUSE_URL_ARG, it),
                                Pair(ProfileFragment.PAGE_ARG, PageTabsAdapter.ProfilePage.BasicInfo)
                        )
                        supportFragmentManager.replaceCurrentFragmentWith(ProfileFragment.TAG, ProfileFragment.Companion::newInstance, args)
                        return@run
                    }
                }
                Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
            }
            R.id.nav_settings -> {
                supportFragmentManager.replaceCurrentFragmentWith(SettingsFragment.TAG, SettingsFragment.Companion::newInstance)
            }
        }
        homeDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
