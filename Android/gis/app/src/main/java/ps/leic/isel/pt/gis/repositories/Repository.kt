package ps.leic.isel.pt.gis.repositories

import android.arch.lifecycle.LiveData

interface Repository {

    fun <T> create(c: Class<T>, url: String, body: String, tag: String): LiveData<Resource<T>>

    fun <T> get(c: Class<T>, url: String, tag: String): LiveData<Resource<T>>

    fun <T> update(c: Class<T>, url: String, body: String, tag: String): LiveData<Resource<T>>

    fun <T> delete(c: Class<T>, url: String, tag: String): LiveData<Resource<T>>

    fun cancelAllPendingRequests(tag: String)
}