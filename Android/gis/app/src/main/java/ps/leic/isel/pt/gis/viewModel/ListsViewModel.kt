package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.ListsDto
import ps.leic.isel.pt.gis.repositories.ListsRepository
import ps.leic.isel.pt.gis.repositories.Resource

class ListsViewModel(application: Application) : AndroidViewModel(application) {

    private val listsRepo: ListsRepository = ListsRepositoryImpl(application)

    private lateinit var lists: LiveData<Resource<ListsDto>>

    fun init(url: String) {
        if (::lists.isInitialized) return
        lists = listsRepo.getLists(url)
    }

    fun getLists(): LiveData<Resource<ListsDto>> {
        return lists
    }
}