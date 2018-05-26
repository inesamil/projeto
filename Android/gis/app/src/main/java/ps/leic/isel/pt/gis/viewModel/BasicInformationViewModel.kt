package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.repositories.Resource

class BasicInformationViewModel(private val app: Application) : AndroidViewModel(app) {

    private var users: LiveData<Resource<UserDto>>? = null

    fun init(url: String) {
        if (users != null) return
        users = ServiceLocator.getRepository(app.applicationContext)
                .get(UserDto::class.java, url, TAG)
    }

    fun getUser(): LiveData<Resource<UserDto>>? {
        return users
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        users = null
    }

    companion object {
        private const val TAG = "BasicInformationViewModel"
    }
}