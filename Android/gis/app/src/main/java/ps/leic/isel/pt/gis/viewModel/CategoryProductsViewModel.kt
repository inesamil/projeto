package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.ProductsDto
import ps.leic.isel.pt.gis.repositories.ProductsRepository
import ps.leic.isel.pt.gis.repositories.Resource

class CategoryProductsViewModel(application: Application) : AndroidViewModel(application) {

    private val productRepo: ProductsRepository = ProductsRepositoryImpl(application)

    private lateinit var products: LiveData<Resource<ProductsDto>>

    fun init(url: String) {
        if (::products.isInitialized) return
        products = productRepo.getProducts(url)
    }

    fun getProducts(): LiveData<Resource<ProductsDto>> {
        return products
    }
}