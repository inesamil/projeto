package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.body.HouseBody
import ps.leic.isel.pt.gis.model.dtos.CharacteristicsDto
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.Resource

class HousesViewModel(private val app: Application) : AndroidViewModel(app) {

    private var houses: LiveData<Resource<HousesDto>>? = null

    fun init(url: String) {
        if (houses != null) return
        houses = ServiceLocator.getRepository(app.applicationContext).get(HousesDto::class.java, url, TAG)
    }

    fun getHouses(): LiveData<Resource<HousesDto>>? {
        return houses
    }

    fun addHouse(house: HouseBody): LiveData<Resource<HouseDto>>? {
        houses?.value?.data?.actions?.addHouse?.let {
            return ServiceLocator.getRepository(app.applicationContext).create(HouseDto::class.java, it.url, mapper.writeValueAsString(house), TAG)
            //TODO: como adicionar esta nova casa ao houses ou forçar o refresh ?. Isto é o modelo observer. no lado do fragmento ficas a espera qe de succes, qnd der fazes set do houses ao adapter.
        }
        return null
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        houses = null
    }

    companion object {
        const val TAG: String = "HousesViewModel"
        private val mapper: ObjectMapper = jacksonObjectMapper()
    }
}