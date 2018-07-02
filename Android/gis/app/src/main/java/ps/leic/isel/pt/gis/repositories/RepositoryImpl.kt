package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.httpRequest.HttpWebService
import ps.leic.isel.pt.gis.utils.HeadersUtils

class RepositoryImpl(private val applicationContext: Context) : Repository {

    override fun <T, E> create(dtoType: Class<T>, errorType: Class<E>, url: String, contentType: String?, body: Any?, tag: String): LiveData<Resource<T, E>> {
        val data = MutableLiveData<Resource<T, E>>()
        data.value = Resource.loading()

        val credentials = ServiceLocator.getCredentialsStore(applicationContext).getCredentials()

        val headers = mutableMapOf<String, String>()
        HeadersUtils.setContentTypeHeader(headers, contentType)
        credentials?.let {
            HeadersUtils.setAuthorizationHeader(headers, it.username, it.password)
        }

        ServiceLocator.getWebService(applicationContext).fetch(
                HttpWebService.Method.POST, url, body, headers, dtoType, errorType,
                { data.value = it }, tag)
        return data
    }

    override fun <T, E> get(dtoType: Class<T>, errorType: Class<E>, url: String, tag: String): LiveData<Resource<T, E>> {
        val data = MutableLiveData<Resource<T, E>>()
        data.value = Resource.loading()

        val credentials = ServiceLocator.getCredentialsStore(applicationContext).getCredentials()

        val headers = mutableMapOf<String, String>()
        credentials?.let {
            HeadersUtils.setAuthorizationHeader(headers, it.username, it.password)
        }

        ServiceLocator.getWebService(applicationContext).fetch(
                HttpWebService.Method.GET, url, null, headers, dtoType, errorType,
                { data.value = it }, tag)
        return data
    }

    override fun <T, E> update(dtoType: Class<T>, errorType: Class<E>, url: String, contentType: String?, body: Any?, tag: String): LiveData<Resource<T, E>> {
        val data = MutableLiveData<Resource<T, E>>()
        data.value = Resource.loading()

        val credentials = ServiceLocator.getCredentialsStore(applicationContext).getCredentials()

        val headers = mutableMapOf<String, String>()
        HeadersUtils.setContentTypeHeader(headers, contentType)
        credentials?.let {
            HeadersUtils.setAuthorizationHeader(headers, it.username, it.password)
        }

        ServiceLocator.getWebService(applicationContext).fetch(
                HttpWebService.Method.PUT, url, body, headers, dtoType, errorType,
                { data.value = it }, tag)
        return data
    }

    override fun <T, E> delete(dtoType: Class<T>, errorType: Class<E>, url: String, tag: String): LiveData<Resource<T, E>> {
        val data = MutableLiveData<Resource<T, E>>()
        data.value = Resource.loading()

        val credentials = ServiceLocator.getCredentialsStore(applicationContext).getCredentials()

        val headers = mutableMapOf<String, String>()
        credentials?.let {
            HeadersUtils.setAuthorizationHeader(headers, it.username, it.password)
        }

        ServiceLocator.getWebService(applicationContext).fetch(
                HttpWebService.Method.DELETE, url, null, headers, dtoType, errorType,
                { data.value = it }, tag)
        return data
    }

    override fun cancelAllPendingRequests(tag: String) {
        ServiceLocator.getWebService(applicationContext).cancelAll(tag)
    }
}