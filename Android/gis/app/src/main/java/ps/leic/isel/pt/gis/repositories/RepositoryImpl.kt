package ps.leic.isel.pt.gis.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.httpRequest.HttpWebService

class RepositoryImpl(val application: Application) : Repository {

    override fun <T> create(c: Class<T>, url: String, body: String, tag: String): LiveData<Resource<T>> {
        val data = MutableLiveData<Resource<T>>()
        data.value = Resource.loading()
        val headers = mutableMapOf<String, String>()
        headers.put("Content-Type", "application/json") //TODO
        val authorization = ServiceLocator.getUserCredentials(application.applicationContext)
        // headers.put("Authorization", authorization)
        ServiceLocator.getWebService(application.applicationContext)?.fetch(
                HttpWebService.Method.POST, url, body, headers, c, {
            data.value = Resource.success(it)
        }, {
            data.value = Resource.error(it)
        }, tag)
        return data
    }

    override fun <T> get(c: Class<T>, url: String, tag: String): LiveData<Resource<T>> {
        val data = MutableLiveData<Resource<T>>()
        data.value = Resource.loading()
        val headers = mutableMapOf<String, String>()
        val authorization = ServiceLocator.getUserCredentials(application.applicationContext)
        // headers.put("Authorization", authorization)
        ServiceLocator.getWebService(application.applicationContext)?.fetch(
                HttpWebService.Method.GET, url, null, headers, c, {
            data.value = Resource.success(it)
        }, {
            data.value = Resource.error(it)
        }, tag)
        return data
    }

    override fun <T> update(c: Class<T>, url: String, body: String, tag: String): LiveData<Resource<T>> {
        val data = MutableLiveData<Resource<T>>()
        data.value = Resource.loading()
        val headers = mutableMapOf<String, String>()
        headers.put("Content-Type", "application/json") //TODO
        val authorization = ServiceLocator.getUserCredentials(application.applicationContext)
        // headers.put("Authorization", authorization)
        ServiceLocator.getWebService(application.applicationContext)?.fetch(
                HttpWebService.Method.PUT, url, body, headers, c, {
            data.value = Resource.success(it)
        }, {
            data.value = Resource.error(it)
        }, tag)
        return data
    }

    override fun <T> delete(c: Class<T>, url: String, tag: String): LiveData<Resource<T>> {
        val data = MutableLiveData<Resource<T>>()
        data.value = Resource.loading()
        val headers = mutableMapOf<String, String>()
        val authorization = ServiceLocator.getUserCredentials(application.applicationContext)
        // headers.put("Authorization", authorization)
        ServiceLocator.getWebService(application.applicationContext)?.fetch(
                HttpWebService.Method.DELETE, url, null, headers, c, {
            data.value = Resource.success(it)
        }, {
            data.value = Resource.error(it)
        }, tag)
        return data
    }
}