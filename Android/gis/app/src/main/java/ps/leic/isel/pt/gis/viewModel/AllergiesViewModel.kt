package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.AllergiesDto
import ps.leic.isel.pt.gis.repositories.AllergiesRepository
import ps.leic.isel.pt.gis.repositories.Resource

class AllergiesViewModel(private val app: Application) : AndroidViewModel(app) {

    private lateinit var allergies: LiveData<Resource<AllergiesDto>>

    fun init(url: String) {
        if (::allergies.isInitialized) return
        allergies = ServiceLocator.getRepository(app).get(AllergiesDto::class.java, url, TAG)
    }

    fun getAllergies(): LiveData<Resource<AllergiesDto>> {
        return allergies
    }

    companion object {
        const val TAG: String = "AllergiesViewModel"
    }
}