package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.ProductsListDto
import ps.leic.isel.pt.gis.repositories.ListsRepository
import ps.leic.isel.pt.gis.repositories.Resource

class ListDetailViewModel(application: Application) : AndroidViewModel(application) {

    // TODO ver se é este repository
    private val listsRepo: ListsRepository = ListsRepositoryImpl(application)

    private lateinit var listDetail: LiveData<Resource<ProductsListDto>>

    fun init(url: String) {
        if (::listDetail.isInitialized) return
        listDetail = listsRepo.getProductsList(url)
    }

    fun getListDetail(): LiveData<Resource<ProductsListDto>> {
        return listDetail
    }
}