package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.ProductsListDto
import ps.leic.isel.pt.gis.repositories.Resource

class ListDetailViewModel(private val app: Application) : AndroidViewModel(app) {

    private var listDetail: LiveData<Resource<ProductsListDto>>? = null

    fun init(url: String) {
        if (listDetail != null) return
        listDetail = ServiceLocator.getRepository(app.applicationContext)
                .get(ProductsListDto::class.java, url, TAG)
    }

    fun getListDetail(): LiveData<Resource<ProductsListDto>>? {
        return listDetail
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        listDetail = null
    }

    companion object {
        private const val TAG = "ListDetailViewModel"
    }
}