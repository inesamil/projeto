package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.ProductDto
import ps.leic.isel.pt.gis.repositories.ProductsRepository
import ps.leic.isel.pt.gis.repositories.Resource

class ProductDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val productsRepo: ProductsRepository = ProductsRepositoryImpl(application)

    private lateinit var product: LiveData<Resource<ProductDto>>

    fun init(url: String) {
        if (::product.isInitialized) return
        product = productsRepo.getProduct(url)
    }

    fun getProduct(): LiveData<Resource<ProductDto>> {
        return product
    }
}