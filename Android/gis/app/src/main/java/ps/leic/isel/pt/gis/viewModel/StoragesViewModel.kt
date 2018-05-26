package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.StoragesDto
import ps.leic.isel.pt.gis.repositories.Resource
import ps.leic.isel.pt.gis.repositories.StoragesRepository

class StoragesViewModel(application: Application) : AndroidViewModel(application) {

    private val storagesRepo: StoragesRepository = StoragesRepositoryImpl(application)

    private lateinit var storages: LiveData<Resource<StoragesDto>>

    fun init(url: String) {
        if (::storages.isInitialized) return
        storages = storagesRepo.getStorages(url)
    }

    fun getStorages(): LiveData<Resource<StoragesDto>> {
        return storages
    }
}