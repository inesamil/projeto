package ps.leic.isel.pt.gis.httpRequest

import ps.leic.isel.pt.gis.repositories.Resource

interface HttpWebService {

    enum class Method {
        GET, POST, PUT, DELETE
    }

    fun <T> fetch(method: Method, url: String, body: Any?, headers: MutableMap<String, String>, dtoType: Class<T>,
                  onSuccess: (Resource<T>) -> Unit, onError: (String?) -> Unit, tag: String)

    fun cancelAll(tag: String)
}