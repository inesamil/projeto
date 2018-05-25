package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.request.WebService
import android.arch.lifecycle.MutableLiveData
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.request.Resource

class HousesRepository constructor(private val service: WebService<HousesDto>) : HouseRepository {

    override fun getHouses(url: String): LiveData<Resource<HousesDto>> {
        val data = MutableLiveData<Resource<HousesDto>>()
        data.value = Resource.loading()
        val headers = mutableMapOf<String, String>()
        // headers.put("Authorization", authorization)
        service.fetch(0, url, null, headers, HousesDto::class.java, {
            data.value = Resource.success(it)
        }, {
            data.value = Resource.error(it?.message)
        }, "ABC")
        return data
    }
}