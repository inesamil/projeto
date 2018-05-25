package ps.leic.isel.pt.gis.httpRequest

interface HttpWebService {

    enum class Method {
        GET, POST, PUT, DELETE
    }

    fun <T> fetch(method: Method, url: String, body: String?, headers: MutableMap<String, String>, dtoType: Class<T>,
              onSuccess: (T) -> Unit, onError: (String?) -> Unit, tag: String)

    fun cancelAll(tag: String)
}