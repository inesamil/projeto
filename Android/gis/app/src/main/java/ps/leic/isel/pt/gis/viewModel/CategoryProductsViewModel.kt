package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.ProductsDto
import ps.leic.isel.pt.gis.repositories.Resource

class CategoryProductsViewModel(private val app: Application) : AndroidViewModel(app) {

    private var products: LiveData<Resource<ProductsDto>>? = null
    private lateinit var url: String

    fun init(url: String) {
        if (products != null && this.url == url) return
        if (products != null) cancel()
        this.url = url
        products = ServiceLocator.getRepository(app.applicationContext)
                .get(ProductsDto::class.java, url, TAG)
    }

    fun getProducts(): LiveData<Resource<ProductsDto>>? {
        return products
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        products = null
    }

    companion object {
        private const val TAG = "CategoryProductsViewModel"
    }
}