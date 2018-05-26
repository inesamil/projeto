package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.repositories.Resource
import ps.leic.isel.pt.gis.repositories.UsersRepository

class BasicInformationViewModel(application: Application) : AndroidViewModel(application) {

    // TODO mudar para user em vez de usersRepository?
    private val usersRepository: UsersRepository = UsersRepositoryImpl(application)

    private lateinit var users: LiveData<Resource<UserDto>>

    fun init(url: String) {
        if (::users.isInitialized) return
        users = usersRepository.getUser(url)
    }

    fun getUser(): LiveData<Resource<UserDto>> {
        return users
    }
}