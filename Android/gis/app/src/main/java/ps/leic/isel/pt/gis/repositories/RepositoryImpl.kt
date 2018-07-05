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
        ServiceLocator.getSmartLock(applicationContext).retrieveCredentials(null, {
            val headers = mutableMapOf<String, String>()
            HeadersUtils.setContentTypeHeader(headers, contentType)
            val password = it.password
            if (password == null) {
                data.value = Resource.error("Password can not be null.")// TODO mensagem
                return@retrieveCredentials
            }
            HeadersUtils.setAuthorizationHeader(headers, it.id, password)
            ServiceLocator.getWebService(applicationContext).fetch(
                    HttpWebService.Method.POST, url, body, headers, dtoType, errorType,
                    { data.value = it }, tag)
        }, {
            data.value = Resource.error("Cannot retrieve credentials")// TODO mensagem
        })
        return data
    }

    override fun <T, E> get(dtoType: Class<T>, errorType: Class<E>, url: String, tag: String): LiveData<Resource<T, E>> {
        val data = MutableLiveData<Resource<T, E>>()
        data.value = Resource.loading()
        ServiceLocator.getSmartLock(applicationContext).retrieveCredentials(null, {
            val headers = mutableMapOf<String, String>()
            val password = it.password
            if (password == null) {
                data.value = Resource.error("Password can not be null.")// TODO mensagem
                return@retrieveCredentials
            }
            HeadersUtils.setAuthorizationHeader(headers, it.id, password)
            ServiceLocator.getWebService(applicationContext).fetch(
                    HttpWebService.Method.GET, url, null, headers, dtoType, errorType,
                    { data.value = it }, tag)
        }, {
            data.value = Resource.error("Cannot retrieve credentials")// TODO mensagem
        })
        return data
    }

    override fun <T, E> update(dtoType: Class<T>, errorType: Class<E>, url: String, contentType: String?, body: Any?, tag: String): LiveData<Resource<T, E>> {
        val data = MutableLiveData<Resource<T, E>>()
        data.value = Resource.loading()
        ServiceLocator.getSmartLock(applicationContext).retrieveCredentials(null, {
            val headers = mutableMapOf<String, String>()
            HeadersUtils.setContentTypeHeader(headers, contentType)
            val password = it.password
            if (password == null) {
                data.value = Resource.error("Password can not be null.")// TODO mensagem
                return@retrieveCredentials
            }
            HeadersUtils.setAuthorizationHeader(headers, it.id, password)
            ServiceLocator.getWebService(applicationContext).fetch(
                    HttpWebService.Method.PUT, url, body, headers, dtoType, errorType,
                    { data.value = it }, tag)
        }, {
            data.value = Resource.error("Cannot retrieve credentials")// TODO mensagem
        })
        return data
    }

    override fun <T, E> delete(dtoType: Class<T>, errorType: Class<E>, url: String, tag: String): LiveData<Resource<T, E>> {
        val data = MutableLiveData<Resource<T, E>>()
        data.value = Resource.loading()
        ServiceLocator.getSmartLock(applicationContext).retrieveCredentials(null, {
            val headers = mutableMapOf<String, String>()
            val password = it.password
            if (password == null) {
                data.value = Resource.error("Password can not be null.")// TODO mensagem
                return@retrieveCredentials
            }
            HeadersUtils.setAuthorizationHeader(headers, it.id, password)
            ServiceLocator.getWebService(applicationContext).fetch(
                    HttpWebService.Method.DELETE, url, null, headers, dtoType, errorType,
                    { data.value = it }, tag)
        }, {
            data.value = Resource.error("Cannot retrieve credentials")// TODO mensagem
        })
        return data
    }

    override fun cancelAllPendingRequests(tag: String) {
        ServiceLocator.getWebService(applicationContext).cancelAll(tag)
    }
}