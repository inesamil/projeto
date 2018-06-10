package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.httpRequest.HttpWebService
import ps.leic.isel.pt.gis.utils.CredentialsStore

class RepositoryImpl(private val applicationContext: Context) : Repository {

    override fun <T> create(c: Class<T>, url: String, contentType: String?, body: String, tag: String): LiveData<Resource<T>> {
        val data = MutableLiveData<Resource<T>>()
        data.value = Resource.loading()

        val headers = mutableMapOf<String, String>()
        contentType?.let {
            headers["Content-Type"] = it
        }
        val credentials: CredentialsStore.Credentials? = ServiceLocator.getCredentialsStore(applicationContext).getCredentials()
        val authorization = ""  //TODO: construir authorization com as credenciais
        // headers.put("Authorization", authorization)

        ServiceLocator.getWebService(applicationContext).fetch(
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
        val credentials: CredentialsStore.Credentials? = ServiceLocator.getCredentialsStore(applicationContext).getCredentials()
        val authorization = ""  //TODO: construir authorization com as credenciais
        // headers.put("Authorization", authorization)
        ServiceLocator.getWebService(applicationContext).fetch(
                HttpWebService.Method.GET, url, null, headers, c, {
            data.value = Resource.success(it)
        }, {
            data.value = Resource.error(it)
        }, tag)
        return data
    }

    override fun <T> update(c: Class<T>, url: String, contentType: String?, body: String, tag: String): LiveData<Resource<T>> {
        val data = MutableLiveData<Resource<T>>()
        data.value = Resource.loading()

        val headers = mutableMapOf<String, String>()
        contentType?.let {
            headers["Content-Type"] = it
        }

        val credentials: CredentialsStore.Credentials? = ServiceLocator.getCredentialsStore(applicationContext).getCredentials()
        val authorization = ""  //TODO: construir authorization com as credenciais
        // headers.put("Authorization", authorization)

        ServiceLocator.getWebService(applicationContext).fetch(
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
        val credentials: CredentialsStore.Credentials? = ServiceLocator.getCredentialsStore(applicationContext).getCredentials()
        val authorization = ""  //TODO: construir authorization com as credenciais
        // headers.put("Authorization", authorization)
        ServiceLocator.getWebService(applicationContext).fetch(
                HttpWebService.Method.DELETE, url, null, headers, c, {
            data.value = Resource.success(it)
        }, {
            data.value = Resource.error(it)
        }, tag)
        return data
    }

    override fun cancelAllPendingRequests(tag: String) {
        ServiceLocator.getWebService(applicationContext).cancelAll(tag)
    }
}