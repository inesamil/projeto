package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData

interface Repository {

    fun <T, E> create(dtoType: Class<T>, errorType: Class<E>, url: String, contentType: String?, body: Any?, tag: String): LiveData<Resource<T, E>>

    fun <T, E> get(dtoType: Class<T>, errorType: Class<E>, url: String, tag: String): LiveData<Resource<T, E>>

    fun <T, E> update(dtoType: Class<T>, errorType: Class<E>, url: String, contentType: String?, body: Any?, tag: String): LiveData<Resource<T, E>>

    fun <T, E> delete(dtoType: Class<T>, errorType: Class<E>, url: String, tag: String): LiveData<Resource<T, E>>

    fun cancelAllPendingRequests(tag: String)
}