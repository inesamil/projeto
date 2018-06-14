package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.ActionDto
import ps.leic.isel.pt.gis.model.body.ListBody
import ps.leic.isel.pt.gis.model.body.ListProductBody
import ps.leic.isel.pt.gis.model.dtos.ListDto
import ps.leic.isel.pt.gis.model.dtos.ListProductDto
import ps.leic.isel.pt.gis.model.dtos.ListsDto
import ps.leic.isel.pt.gis.repositories.Resource

class ListsViewModel(private val app: Application) : AndroidViewModel(app) {

    private lateinit var url: String
    private var lists: LiveData<Resource<ListsDto>>? = null

    fun init(url: String) {
        if (lists != null && this.url == url) return
        if (lists != null) cancel()
        this.url = url
        lists = ServiceLocator.getRepository(app.applicationContext).get(ListsDto::class.java, url, TAG)
    }

    fun reload(url: String) {
        if (lists != null) cancel()
        this.url = url
        lists = ServiceLocator.getRepository(app.applicationContext).get(ListsDto::class.java, url, TAG)
    }

    fun getLists(): LiveData<Resource<ListsDto>>? {
        return lists
    }

    fun addList(list: ListBody): LiveData<Resource<ListDto>>? {
        lists?.value?.data?.actions?.addList?.let {
            return ServiceLocator
                    .getRepository(app.applicationContext)
                    .create(ListDto::class.java, it.url, it.contentType, list, TAG)
        }
        return null
    }

    fun addProductsToList(listProductBody: ListProductBody, actionDto: ActionDto): LiveData<Resource<ListProductDto>> {
        return ServiceLocator.getRepository(app.applicationContext)
                .create(ListProductDto::class.java, actionDto.url, actionDto.contentType, listProductBody, TAG)
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        lists = null
    }

    companion object {
        private const val TAG = "ListsViewModel"
    }
}