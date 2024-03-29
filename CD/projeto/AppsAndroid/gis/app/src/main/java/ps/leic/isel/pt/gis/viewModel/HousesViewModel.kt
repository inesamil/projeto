package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.body.HouseBody
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.Resource

class HousesViewModel(private val app: Application) : AndroidViewModel(app) {

    private lateinit var url: String
    private var houses: LiveData<Resource<HousesDto, ErrorDto>>? = null

    fun init(url: String) {
        if (houses != null && this.url == url) return
        if (houses != null) cancel()
        this.url = url
        houses = ServiceLocator.getRepository(app.applicationContext)
                .get(HousesDto::class.java, ErrorDto::class.java, url, TAG)
    }

    fun getHouses(): LiveData<Resource<HousesDto, ErrorDto>>? {
        return houses
    }

    fun addHouse(house: HouseBody): LiveData<Resource<HouseDto, ErrorDto>>? {
        houses?.value?.data?.actions?.addHouse?.let {
            return ServiceLocator.getRepository(app.applicationContext)
                    .create(HouseDto::class.java, ErrorDto::class.java, it.url, it.contentType, house, TAG)
        }
        return null
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        houses = null
    }

    companion object {
        const val TAG: String = "HousesViewModel"
    }
}