package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.body.HouseAllergiesBody
import ps.leic.isel.pt.gis.model.dtos.HouseAllergiesDto
import ps.leic.isel.pt.gis.model.dtos.HouseAllergyDto
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

    fun addHouseAllergies(houseAllergies: HouseAllergiesBody): LiveData<Resource<HouseAllergyDto>>? {
        allergies?.value?.data?.actions?.updateHouseAllergies?.let {
            return ServiceLocator
                    .getRepository(app.applicationContext)
                    .update(HouseAllergyDto::class.java, it.url, it.contentType, houseAllergies, TAG)
        }
        return null
    }

    fun deleteAllHouseAllergies() : LiveData<Resource<HouseAllergiesDto>>? {
        allergies?.value?.data?.actions?.deleteHouseAllergies?.let {
            return ServiceLocator
                    .getRepository(app.applicationContext)
                    .delete(HouseAllergiesDto::class.java, it.url, TAG)
        }
        return null
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        allergies = null
    }

    companion object {
        const val TAG: String = "AllergiesViewModel"
    }
}