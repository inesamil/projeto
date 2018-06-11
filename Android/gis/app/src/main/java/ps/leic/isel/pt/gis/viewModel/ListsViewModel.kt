package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.json.JSONObject
import org.json.JSONStringer
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.body.ListBody
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

    fun addList(list: ListBody): LiveData<Resource<ListDto>>? {
        lists?.value?.data?.actions?.addList?.let {
            return ServiceLocator
                    .getRepository(app.applicationContext)
                    .create(ListDto::class.java, it.url, it.contentType, list, HousesViewModel.TAG)
        }
        return null
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        lists = null
    }

    companion object {
        private const val TAG = "ListsViewModel"
        private val mapper: ObjectMapper = jacksonObjectMapper()
    }
}