package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.GisApplication
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.body.UserBody
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.model.dtos.UsersDto
import ps.leic.isel.pt.gis.repositories.Resource

class UsersViewModel(private val app: Application) : AndroidViewModel(app) {

    private lateinit var url: String

    private var users: LiveData<Resource<UsersDto, ErrorDto>>? = null

    fun init(url: String) {
        if (users != null && this.url == url) return
        if (users != null) cancel()
        this.url = url
        if (users?.value?.data != null) return
        users = ServiceLocator.getRepository(app.applicationContext)
                .get(UsersDto::class.java, ErrorDto::class.java, url, TAG)
    }

    fun getUsers() : LiveData<Resource<UsersDto, ErrorDto>>? {
        return users
    }

    fun addUser(user: UserBody) : LiveData<Resource<UserDto, ErrorDto>>? {
        val gisApplication = app as GisApplication
        val index = gisApplication.index
        index.getUsersAction()?.let {
            return ServiceLocator.getRepository(app.applicationContext)
                    .create(UserDto::class.java, ErrorDto::class.java, it.url, it.contentType, user, TAG)
        }
        return null
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        users = null
    }

    companion object {
        private const val TAG = "UsersViewModel"
    }
}