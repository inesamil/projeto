package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
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

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        houses = null
    }

    companion object {
        const val TAG: String = "HousesViewModel"
    }
}