package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.GisApplication
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.body.UserBody
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.repositories.Resource

class UsersViewModel(private val app: Application) : AndroidViewModel(app) {

    private var user: LiveData<Resource<UserDto, ErrorDto>>? = null

    fun init(url: String) {
        if (user?.value?.data != null) return
        user = ServiceLocator.getRepository(app.applicationContext)
                .get(UserDto::class.java, ErrorDto::class.java, url, TAG)
    }

    fun getUser(): LiveData<Resource<UserDto, ErrorDto>>? {
        return user
    }


    fun addUser(user: UserBody) : LiveData<Resource<UserDto, ErrorDto>>? {
        val gisApplication = app as GisApplication
        val index = gisApplication.index
        index.getUsersAction()?.let {
            return ServiceLocator.getRepository(app.applicationContext)
                    .create(UserDto::class.java, ErrorDto::class.java, it.url, it.contentType, user, SplashScreenViewModel.TAG)
        }
        return null
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        user = null
    }

    companion object {
        private const val TAG = "UsersViewModel"
    }
}