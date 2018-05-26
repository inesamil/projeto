package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.ProductDto
import ps.leic.isel.pt.gis.repositories.Resource

class ProductDetailViewModel(private val app: Application) : AndroidViewModel(app) {

    private var product: LiveData<Resource<ProductDto>>? = null

    fun init(url: String) {
        if (product != null) return
        product = ServiceLocator.getRepository(app.applicationContext).get(ProductDto::class.java, url, TAG)
    }

    fun getProduct(): LiveData<Resource<ProductDto>>? {
        return product
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        product = null
    }

    companion object {
        private const val TAG: String = "ProductDetailViewModel"
    }
}