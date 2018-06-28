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

class BasicInformationViewModel(private val app: Application) : AndroidViewModel(app) {

    private var users: LiveData<Resource<UserDto, ErrorDto>>? = null

    fun init(url: String) {
        if (users?.value?.data != null) return
        users = ServiceLocator.getRepository(app.applicationContext)
                .get(UserDto::class.java, ErrorDto::class.java, url, TAG)
    }

    fun getUser(): LiveData<Resource<UserDto, ErrorDto>>? {
        return users
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
        users = null
    }

    companion object {
        private const val TAG = "BasicInformationViewModel"
    }
}