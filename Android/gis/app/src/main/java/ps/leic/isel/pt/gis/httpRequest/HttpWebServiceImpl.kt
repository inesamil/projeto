package ps.leic.isel.pt.gis.httpRequest

import android.content.Context
import com.android.volley.*
import ps.leic.isel.pt.gis.R
import ps.leic.isel.pt.gis.repositories.Resource
import ps.leic.isel.pt.gis.volley.RequestQueue
import ps.leic.isel.pt.gis.volley.Requester

class HttpWebServiceImpl(private val applicationContext: Context) : HttpWebService {

    private val methodsMapper = HashMap<HttpWebService.Method, Int>()

    init {
        methodsMapper[HttpWebService.Method.GET] = Request.Method.GET
        methodsMapper[HttpWebService.Method.POST] = Request.Method.POST
        methodsMapper[HttpWebService.Method.PUT] = Request.Method.PUT
        methodsMapper[HttpWebService.Method.DELETE] = Request.Method.DELETE
    }

    override fun <T, E> fetch(
            method: HttpWebService.Method, url: String, body: Any?, headers: MutableMap<String, String>,
            dtoType: Class<T>, errorType: Class<E>, onSuccess: (Resource<T, E>) -> Unit, onError: (String) -> Unit,
            tag: String
    ) {
        methodsMapper[method]?.let {
            val requester = Requester(it, url, body, headers, dtoType, errorType, onSuccess, { error -> onVolleyError(error, onError) })
            RequestQueue.getInstance(applicationContext).addToRequestQueue(requester)
            requester.tag = tag
            return
        }
        onError("HttpWebServiceImpl does not support $method")
    }

    override fun cancelAll(tag: String) {
        RequestQueue.getInstance(applicationContext).requestQueue.cancelAll(tag)
    }

    private fun onVolleyError(error: VolleyError?, onError: (String) -> Unit) {
        val message: String = when {
            error is TimeoutError -> applicationContext.getString(R.string.could_not_connect_to_server)
            error is ServerError ->  applicationContext.getString(R.string.could_not_connect_to_server)
            error is NetworkError -> applicationContext.getString(R.string.network_error_has_occurred)
            error is NoConnectionError -> applicationContext.getString(R.string.network_error_has_occurred)
            //TODO: error is ClientError ->
            else -> applicationContext.getString(R.string.unfortunately_an_error_has_occurred)
        }
        onError(message)
    }
}