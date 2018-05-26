package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.UserDto
import ps.leic.isel.pt.gis.model.dtos.UserHousesDto

interface UsersRepository {
    fun getUser(): LiveData<Resource<UserDto>>

    fun getUserHouses(): LiveData<Resource<UserHousesDto>>

    fun putUser(): LiveData<Resource<UserDto>>

    fun deleteUser(): LiveData<Resource<UserDto>>
}