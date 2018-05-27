package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.HouseAllergiesDto
import ps.leic.isel.pt.gis.repositories.Resource

class AllergiesViewModel(private val app: Application) : AndroidViewModel(app) {

    private var allergies: LiveData<Resource<HouseAllergiesDto>>? = null

    fun init(url: String) {
        if (allergies != null) return
        allergies = ServiceLocator.getRepository(app).get(HouseAllergiesDto::class.java, url, TAG)
    }

    fun getAllergies(): LiveData<Resource<HouseAllergiesDto>>? {
        return allergies
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        allergies = null
    }

    companion object {
        const val TAG: String = "AllergiesViewModel"
    }
}