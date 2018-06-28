package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.hypermedia.problemDetails.ProblemDetails
import ps.leic.isel.pt.gis.model.dtos.CategoriesDto
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.repositories.Resource

class CategoriesViewModel(private val app: Application) : AndroidViewModel(app) {

    private var categories: LiveData<Resource<CategoriesDto, ErrorDto>>? = null

    fun init(url: String) {
        if (categories != null) return
        categories = ServiceLocator.getRepository(app.applicationContext)
                .get(CategoriesDto::class.java, ErrorDto::class.java, url, TAG)
    }

    fun getCategories(): LiveData<Resource<CategoriesDto, ErrorDto>>? {
        return categories
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        categories = null
    }

    companion object {
        private const val TAG = "CategoriesViewModel"
    }
}