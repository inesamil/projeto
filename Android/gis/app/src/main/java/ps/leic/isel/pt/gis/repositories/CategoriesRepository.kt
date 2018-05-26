package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.CategoriesDto
import ps.leic.isel.pt.gis.model.dtos.CategoryDto

interface CategoriesRepository {

    fun getCategories(): LiveData<Resource<CategoriesDto>>

    fun getCetegory(): LiveData<Resource<CategoryDto>>
}