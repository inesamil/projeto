package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.model.dtos.HousesDto

interface HousesRepository {

    fun getHouses(url: String): LiveData<Resource<HousesDto>>
}