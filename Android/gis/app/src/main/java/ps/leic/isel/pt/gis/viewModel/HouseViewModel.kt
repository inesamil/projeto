package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.HousesRepository
import ps.leic.isel.pt.gis.request.Resource
import ps.leic.isel.pt.gis.request.Service

class HouseViewModel(application: Application) : AndroidViewModel(application) {

    private val housesRepo: HousesRepository = HousesRepository(Service(application))

    private var houses: LiveData<Resource<HousesDto>>? = null

    fun init(url: String) {
        if (houses != null)
            return
        houses = housesRepo.getHouses(url)
    }

    fun getHouses(): LiveData<Resource<HousesDto>>? {
        return houses
    }
}