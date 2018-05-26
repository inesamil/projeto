package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.CategoriesDto
import ps.leic.isel.pt.gis.repositories.CategoriesRepository
import ps.leic.isel.pt.gis.repositories.Resource

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {

    private val categoriesRepo: CategoriesRepository = CategoriesRepositoryImpl(application)

    private lateinit var categories: LiveData<Resource<CategoriesDto>>

    fun init(url: String) {
        if (::categories.isInitialized) return
        categories = categoriesRepo.getCategories(url)
    }

    fun getCategories(): LiveData<Resource<CategoriesDto>> {
        return categories
    }
}