package ps.leic.isel.pt.gis.httpRequest

import android.content.Context
import com.android.volley.Request
import ps.leic.isel.pt.gis.utils.RequestQueue
import ps.leic.isel.pt.gis.utils.Requester

class HttpWebServiceImpl(private val applicationContext: Context) : HttpWebService {

    private val methodsMapper = HashMap<HttpWebService.Method, Int>()

    init {
        methodsMapper[HttpWebService.Method.GET] = Request.Method.GET
        methodsMapper[HttpWebService.Method.POST] = Request.Method.POST
        methodsMapper[HttpWebService.Method.PUT] = Request.Method.PUT
        methodsMapper[HttpWebService.Method.DELETE] = Request.Method.DELETE
    }

    override fun <T> fetch(method: HttpWebService.Method, url: String, body: Any?, headers: MutableMap<String, String>,
                           dtoType: Class<T>, onSuccess: (T) -> Unit, onError: (String?) -> Unit,
                           tag: String) {
        methodsMapper[method]?.let {
            val requester = Requester(it, url, body, headers, dtoType, onSuccess, { onError(it?.message) })
            RequestQueue.getInstance(applicationContext).addToRequestQueue(requester)
            requester.tag = tag
            return
        }
        onError("HttpWebServiceImpl does not support $method")
    }

    override fun cancelAll(tag: String) {
        RequestQueue.getInstance(applicationContext).requestQueue.cancelAll(tag)
    }
}