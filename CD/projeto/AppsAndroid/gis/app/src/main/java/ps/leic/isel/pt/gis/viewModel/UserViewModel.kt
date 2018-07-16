package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.repositories.Resource

class UserViewModel(private val app: Application) : AndroidViewModel(app) {

    private var user: LiveData<Resource<UserDto, ErrorDto>>? = null

    fun init(url: String) {
        if (user?.value?.data != null) return
        user = ServiceLocator.getRepository(app.applicationContext)
                .get(UserDto::class.java, ErrorDto::class.java, url, TAG)
    }

    fun getUser(): LiveData<Resource<UserDto, ErrorDto>>? {
        return user
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        user = null
    }

    companion object {
        private const val TAG = "UserViewModel"
    }
}