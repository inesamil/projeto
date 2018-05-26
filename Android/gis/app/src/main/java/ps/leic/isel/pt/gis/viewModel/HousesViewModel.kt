package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.HousesRepository
import ps.leic.isel.pt.gis.repositories.Resource
import ps.leic.isel.pt.gis.repositories.implementations.HousesRepositoryImpl

class HousesViewModel(private val app: Application) : AndroidViewModel(app) {

    private lateinit var houses: LiveData<Resource<HousesDto>>

    fun init(url: String) {
        if (::houses.isInitialized) return
        houses = ServiceLocator.getRepository(app.applicationContext).get(HousesDto::class.java, url, TAG)
    }

    fun getHouses(): LiveData<Resource<HousesDto>> {
        return houses
    }

    companion object {
        const val TAG: String = "HousesViewModel"
    }
}