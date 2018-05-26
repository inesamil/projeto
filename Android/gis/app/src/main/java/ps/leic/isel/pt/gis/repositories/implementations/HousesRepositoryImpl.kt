package ps.leic.isel.pt.gis.repositories.implementations

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.httpRequest.HttpWebService
import ps.leic.isel.pt.gis.model.dtos.HousesDto
import ps.leic.isel.pt.gis.repositories.HousesRepository
import ps.leic.isel.pt.gis.repositories.Resource

class HousesRepositoryImpl(private val application: Application) : HousesRepository {

    override fun getHouses(url: String): LiveData<Resource<HousesDto>> {
        val data = MutableLiveData<Resource<HousesDto>>()
        data.value = Resource.loading()
        val headers = mutableMapOf<String, String>()
        val authorization = ServiceLocator.getUserCredentials(application.applicationContext)
        // headers.put("Authorization", authorization)
        ServiceLocator.getWebService(application.applicationContext)?.fetch(
                HttpWebService.Method.GET, url, null, headers, HousesDto::class.java, {
            data.value = Resource.success(it)
        }, {
            data.value = Resource.error(it)
        }, TAG)
        return data
    }

    fun cancelAllPendingResources() {
        ServiceLocator.getWebService(application.applicationContext)?.cancelAll(TAG)
    }

    companion object {
        private const val TAG = "HousesRepositoryImpl"
    }
}