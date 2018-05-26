package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.HousesRepository
import ps.leic.isel.pt.gis.repositories.Resource
import ps.leic.isel.pt.gis.repositories.implementations.HousesRepositoryImpl

class HousesViewModel(application: Application) : AndroidViewModel(application) {

    private val housesRepo: HousesRepository = HousesRepositoryImpl(application)

    private lateinit var houses: LiveData<Resource<HousesDto>>

    fun init(url: String) {
        if (::houses.isInitialized) return
        houses = housesRepo.getHouses(url)
    }

    fun getHouses(): LiveData<Resource<HousesDto>> {
        return houses
    }
}