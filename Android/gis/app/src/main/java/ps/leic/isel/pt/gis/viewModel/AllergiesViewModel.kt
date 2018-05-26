package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.AllergiesDto
import ps.leic.isel.pt.gis.repositories.AllergiesRepository
import ps.leic.isel.pt.gis.repositories.Resource

class AllergiesViewModel(application: Application) : AndroidViewModel(application) {

    private val allergiesRepository: AllergiesRepository = AllergiesRepositoryImpl(application)

    private lateinit var allergies: LiveData<Resource<AllergiesDto>>

    fun init(url: String) {
        if (::allergies.isInitialized) return
        allergies = allergiesRepository.getAllergies(url)
    }

    fun getAllergies(): LiveData<Resource<AllergiesDto>> {
        return allergies
    }
}