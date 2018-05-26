package ps.leic.isel.pt.gis.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.httpRequest.HttpWebService

class Repository (val application: Application){

    inline fun <reified T> get(url: String, tag: String) : LiveData<Resource<T>> {
        val data = MutableLiveData<Resource<T>>()
        data.value = Resource.loading()
        val headers = mutableMapOf<String, String>()
        val authorization = ServiceLocator.getUserCredentials(application.applicationContext)
        // headers.put("Authorization", authorization)
        ServiceLocator.getWebService(application.applicationContext)?.fetch(
                HttpWebService.Method.GET, url, null, headers, T::class.java, {
            data.value = Resource.success(it)
        }, {
            data.value = Resource.error(it)
        }, tag)
        return data
    }

    inline fun <reified T> post(url: String, body: String, tag: String) : LiveData<Resource<T>> {
        val data = MutableLiveData<Resource<T>>()
        data.value = Resource.loading()
        val headers = mutableMapOf<String, String>()
        headers.put("Content-Type", "application/json") //TODO
        val authorization = ServiceLocator.getUserCredentials(application.applicationContext)
        // headers.put("Authorization", authorization)
        ServiceLocator.getWebService(application.applicationContext)?.fetch(
                HttpWebService.Method.POST, url, body, headers, T::class.java, {
            data.value = Resource.success(it)
        }, {
            data.value = Resource.error(it)
        }, tag)
        return data
    }

    inline fun <reified T> put(url: String, body: String, tag: String) : LiveData<Resource<T>> {
        val data = MutableLiveData<Resource<T>>()
        data.value = Resource.loading()
        val headers = mutableMapOf<String, String>()
        headers.put("Content-Type", "application/json") //TODO
        val authorization = ServiceLocator.getUserCredentials(application.applicationContext)
        // headers.put("Authorization", authorization)
        ServiceLocator.getWebService(application.applicationContext)?.fetch(
                HttpWebService.Method.PUT, url, body, headers, T::class.java, {
            data.value = Resource.success(it)
        }, {
            data.value = Resource.error(it)
        }, tag)
        return data
    }

    inline fun <reified T> delete(url: String, tag: String) : LiveData<Resource<T>> {
        val data = MutableLiveData<Resource<T>>()
        data.value = Resource.loading()
        val headers = mutableMapOf<String, String>()
        val authorization = ServiceLocator.getUserCredentials(application.applicationContext)
        // headers.put("Authorization", authorization)
        ServiceLocator.getWebService(application.applicationContext)?.fetch(
                HttpWebService.Method.DELETE, url, null, headers, T::class.java, {
            data.value = Resource.success(it)
        }, {
            data.value = Resource.error(it)
        }, tag)
        return data
    }
}