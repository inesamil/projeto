package ps.leic.isel.pt.gis.uis.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaScannerConnection
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.ListProductDto
import ps.leic.isel.pt.gis.model.dtos.StorageDto
import ps.leic.isel.pt.gis.uis.fragments.*
import ps.leic.isel.pt.gis.utils.getCurrentFragment
import ps.leic.isel.pt.gis.utils.replaceCurrentFragmentWith
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class HomeActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        HomePageFragment.OnHomeFragmentInteractionListener,
        HousesFragment.OnHousesFragmentInteractionListener,
        NewHouseDialogFragment.OnNewHouseDialogFragmentInteractionListener,
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

    /**
     * Fragment Listeners
     */
    // Listener for HomePageFragment
    override fun onMyPantryInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        ServiceLocator
                .getSmartLock(applicationContext)
                .retrieveCredentials(null, {
                    index.getHousesUrl(it.id)?.let {
                        supportFragmentManager.replaceCurrentFragmentWith(StockItemListFragment.TAG, StockItemListFragment.Companion::newInstance, it)
                    }
                }, {
                    Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
                    logout()
                })
    }

    // Listener for HomePageFragment
    override fun onMyHousesInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        ServiceLocator
                .getSmartLock(applicationContext)
                .retrieveCredentials(null, { credentials ->
                    index.getHousesUrl(credentials.id)?.let {url ->
                        val args: Map<String, Any> = mapOf(
                                Pair(HousesFragment.URL_ARG, url),
                                Pair(HousesFragment.USERNAME_ARG, credentials.id)
                        )
                        supportFragmentManager.replaceCurrentFragmentWith(HousesFragment.TAG, HousesFragment.Companion::newInstance, args)
                        return@retrieveCredentials
                    }
                    Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
                }, {
                    Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
                    logout()
                })
    }

    // Listener for HomePageFragment
    override fun onMyProfileInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        ServiceLocator
                .getSmartLock(applicationContext)
                .retrieveCredentials(null, {
                    index.getUserUrl(it.id)?.let {
                        supportFragmentManager.replaceCurrentFragmentWith(BasicInformationFragment.TAG, BasicInformationFragment.Companion::newInstance, it)
                        return@retrieveCredentials
                    }
                    Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
                }, {
                    Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
                    logout()
                })
    }

    // Listener for HomePageFragment
    override fun onMyListsInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        ServiceLocator
                .getSmartLock(applicationContext)
                .retrieveCredentials(null, {credentials ->
                    index.getUserListUrl(credentials.id)?.let {url ->
                        val args: Map<String, Any> = mapOf(
                                Pair(HousesFragment.URL_ARG, url),
                                Pair(HousesFragment.USERNAME_ARG, credentials.id)
                        )
                        supportFragmentManager.replaceCurrentFragmentWith(ListsFragment.TAG, ListsFragment.Companion::newInstance, args)
                    }
                }, {
                    Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
                    logout()
                })
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

    override fun onInvitationsSearchInteraction(house: HouseDto) {
        val dialog = InvitationsSearchDialogFragment.newInstance()
        dialog.setOnInvitationsSearchDialogFragmentListener(object : InvitationsSearchDialogFragment.OnInvitationsSearchDialogFragmentListener {
            override fun onSendInvitation(username: String) {
                val fragment = supportFragmentManager.findFragmentByTag(HousesFragment.TAG) as? HousesFragment
                fragment?.onInvitationSend(house, username)
            }
        })
        dialog.show(supportFragmentManager, DeleteConfirmationDialogFragment.TAG)
    }

    // Listener for CategoriesFragement interaction
    override fun onCategoryInteraction(url: String, categoryName: String) {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        ServiceLocator
                .getSmartLock(applicationContext)
                .retrieveCredentials(null, {
                    index.getUserListUrl(it.id)?.let {
                        val args: Map<String, Any> = mapOf(
                                Pair(CategoryProductsFragment.URL_ARG, url),
                                Pair(CategoryProductsFragment.CATEGORY_NAME_ARG, categoryName)
                        )
                        supportFragmentManager.replaceCurrentFragmentWith(CategoryProductsFragment.TAG, CategoryProductsFragment.Companion::newInstance, args)
                    }
                }, {
                    Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
                    logout()
                })
    }

    // Listener for CategoryProductsFragement interaction
    override fun onAddProductToListInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        ServiceLocator
                .getSmartLock(applicationContext)
                .retrieveCredentials(null, {
                    index.getCategoriesUrl()?.let {
                        val dialog = AddProductToListDialogFragment.newInstance(it)
                        dialog.show(supportFragmentManager, AddProductToListDialogFragment.TAG)
                    }
                }, {
                    Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
                    logout()
                })
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
        ServiceLocator
                .getSmartLock(applicationContext)
                .retrieveCredentials(null, {
                    index.getHousesUrl(it.id)?.let {
                        val fragment = NewListDialogFragment.newInstance(it)
                        fragment.show(supportFragmentManager, NewListDialogFragment.TAG)
                    }
                }, {
                    Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
                    logout()
                })
    }

    override fun onAddList(list: List) {
        val fragment = supportFragmentManager.findFragmentByTag(ListsFragment.TAG) as? ListsFragment
        fragment?.onAddList(list)
    }

    // Listener for ListsFragment interaction
    override fun onFiltersInteraction() {
        val gisApplication = application as GisApplication
        val index = gisApplication.index
        ServiceLocator
                .getSmartLock(applicationContext)
                .retrieveCredentials(null, {
                    index.getHousesUrl(it.id)?.let {
                        val fragment = ListsFiltersDialogFragment.newInstance(it)
                        fragment.show(supportFragmentManager, ListsFiltersDialogFragment.TAG)
                    }
                }, {
                    Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
                    logout()
                })
    }

    // Listener for ListDetailFragment interaction
    override fun onListDownload(listProducts: Array<ListProductDto>) {
        if (!isExternalStorageWritable()) {
            Toast.makeText(this, "Midia não está disponivel", Toast.LENGTH_SHORT).show() // TODO meter nas strings
            return
        }
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(path, "test") // TODO mudar
        try {
            if (!path.mkdirs()) {
                Toast.makeText(this, "Cannot save, cannot create pictures directory.", Toast.LENGTH_SHORT).show() // TODO strings
                return
            }

            val dest = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(dest)
            val paint = Paint()
            paint.textSize = 35F
            paint.color = Color.BLACK
            paint.style = Paint.Style.FILL
            canvas.drawBitmap(dest, 0F, 0F, null)
            val height = paint.measureText("yY")
            val width = paint.measureText("test")
            canvas.drawText("test", width, height + 15F, paint)

            dest.compress(Bitmap.CompressFormat.PNG, 50, FileOutputStream(file))

            // Very simple code to copy a picture from the application's
            // resource into the external file.  Note that this code does
            // no error checking, and assumes the picture is small (does not
            // try to copy it in chunks).  Note that if external storage is
            // not currently mounted this will silently fail.


            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(this, arrayOf(file.toString()), null) { path, uri ->
                Log.i("ExternalStorage", "Scanned $path:")
                Log.i("ExternalStorage", "-> uri=$uri")
            }
        } catch (e: IOException) {
            // Unable to create file, likely because external storage is
            // not currently mounted.
            Toast.makeText(this, "Midia não está disponivel", Toast.LENGTH_SHORT).show() // TODO meter nas strings
            Log.w("HomeActivity", "Error writing $file", e) // TODO qual a tag a usar?
        }


        // TODO: download
        // Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_LONG).show()
    }

    // Checks if external storage is available for read and write
    fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    // Listener for ListsFiltersDialogFragment interaction
    override fun onFiltersApply(systemLists: Boolean, userLists: Boolean, sharedLists: Boolean, houses: Array<HouseDto>?, loggedInUser: String) {
        val listsFragment = supportFragmentManager.findFragmentByTag(ListsFragment.TAG) as? ListsFragment
        listsFragment?.onFiltersApplied(systemLists, userLists, sharedLists, houses, loggedInUser)
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
        val gisApplication = application as GisApplication
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.invitationsItem -> {
                val index = gisApplication.index
                ServiceLocator
                        .getSmartLock(applicationContext)
                        .retrieveCredentials(null, {
                            val url: String? = index.getInvitationsUrl(it.id)
                            url?.let {
                                supportFragmentManager.replaceCurrentFragmentWith(InvitationsFragment.TAG, InvitationsFragment.Companion::newInstance, url)
                                return@retrieveCredentials
                            }
                            Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
                        }, {
                            Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
                            logout()
                        })
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
                ServiceLocator
                        .getSmartLock(applicationContext)
                        .retrieveCredentials(null, {credentials ->
                            val url: String? = index.getUserListUrl(credentials.id)
                            url?.let {
                                val args: Map<String, Any> = mapOf(
                                        Pair(HousesFragment.URL_ARG, it),
                                        Pair(HousesFragment.USERNAME_ARG, credentials.id)
                                )
                                supportFragmentManager.replaceCurrentFragmentWith(ListsFragment.TAG, ListsFragment.Companion::newInstance, args)
                                return@retrieveCredentials
                            }
                            Toast.makeText(this, getString(R.string.functionality_not_available), Toast.LENGTH_SHORT).show()
                        }, {
                            Toast.makeText(this, getString(R.string.user_needs_to_login_first), Toast.LENGTH_SHORT).show()
                            logout()
                        })
                return true
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
            R.id.nav_settings -> {
                supportFragmentManager.replaceCurrentFragmentWith(SettingsFragment.TAG, SettingsFragment.Companion::newInstance)
            }
            R.id.nav_logout -> run {
                logout()
            }
        }
        homeDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    /***
     * Private Methods
     ***/

    private fun logout() {
        ServiceLocator
                .getSmartLock(applicationContext)
                .disableAutoSignIn()
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
