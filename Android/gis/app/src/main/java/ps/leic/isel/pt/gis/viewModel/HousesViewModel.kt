package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.implementations.HousesRepository
import ps.leic.isel.pt.gis.repositories.Resource
import ps.leic.isel.pt.gis.httpRequest.HttpWebServiceImpl

class HousesViewModel(application: Application) : AndroidViewModel(application) {

    private val housesRepo: HousesRepository = HousesRepository(application)

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