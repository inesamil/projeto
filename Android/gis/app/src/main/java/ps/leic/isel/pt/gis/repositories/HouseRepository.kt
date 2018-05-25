package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import dagger.Component
import ps.leic.isel.pt.gis.model.inputModel.HousesDto
import ps.leic.isel.pt.gis.request.Resource

interface HouseRepository {

    fun getHouses(url: String): LiveData<Resource<HousesDto>>
}