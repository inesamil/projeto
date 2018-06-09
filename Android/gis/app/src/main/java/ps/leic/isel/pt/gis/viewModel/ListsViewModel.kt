package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.ListDto
import ps.leic.isel.pt.gis.model.dtos.ListsDto
import ps.leic.isel.pt.gis.repositories.Resource

class ListsViewModel(private val app: Application) : AndroidViewModel(app) {

    private var lists: LiveData<Resource<ListsDto>>? = null

    fun init(url: String) {
        if (lists != null) return
        lists = ServiceLocator.getRepository(app.applicationContext)
                .get(ListsDto::class.java, url, TAG)
    }

    fun getLists(): LiveData<Resource<ListsDto>>? {
        return lists
    }

    fun addList(houseId: Long, listName: String, username: String, shareable: Boolean): LiveData<Resource<ListDto>>? {
        lists?.value?.data?.actions?.addList?.let {
            //TODO: return ServiceLocator.getRepository(app.applicationContext).create(ListDto::class.java, it.url, it.getBo, HousesViewModel.TAG)
            //TODO: como adicionar esta nova casa ao houses ou forçar o refresh ?
        }
        return null
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        lists = null
    }

    companion object {
        private const val TAG = "ListsViewModel"
    }
}