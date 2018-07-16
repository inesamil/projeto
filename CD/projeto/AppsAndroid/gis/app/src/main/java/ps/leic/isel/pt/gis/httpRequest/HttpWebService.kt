package ps.leic.isel.pt.gis.httpRequest

import ps.leic.isel.pt.gis.repositories.Resource

interface HttpWebService {

    enum class Method {
        GET, POST, PUT, DELETE
    }

    fun <T, E> fetch(method: Method, url: String, body: Any?, headers: MutableMap<String, String>,
                     dtoType: Class<T>, errorType: Class<E>, callback: (Resource<T, E>) -> Unit,
                     tag: String)

    fun cancelAll(tag: String)
}