package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.StoragesDto
import ps.leic.isel.pt.gis.repositories.Resource

class StoragesViewModel(private val app: Application) : AndroidViewModel(app) {

    private var storages: LiveData<Resource<StoragesDto>>? = null

    fun init(url: String) {
        if (storages != null) return
        storages = ServiceLocator.getRepository(app.applicationContext).get(StoragesDto::class.java, url, TAG)
    }

    fun getStorages(): LiveData<Resource<StoragesDto>>? {
        return storages
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        storages = null
    }

    companion object {
        private const val TAG: String = "StoragesViewModel"
    }
}