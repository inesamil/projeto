package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.model.dtos.MemberDto
import ps.leic.isel.pt.gis.model.dtos.MembersDto

interface HousesRepository {

    fun getHouses(url: String): LiveData<Resource<HousesDto>>

    fun getHouse(url: String): LiveData<Resource<HouseDto>>

    fun getUsersHouse(url: String): LiveData<Resource<MembersDto>>

    fun postHouse(url: String): LiveData<Resource<HousesDto>>

    fun putHouse(url: String): LiveData<Resource<HouseDto>>

    fun putUserHouse(url: String): LiveData<Resource<MemberDto>>

    fun deleteHouse(url: String): LiveData<Resource<HouseDto>>

    fun deleteUserHouse(url: String): LiveData<Resource<MemberDto>>
}