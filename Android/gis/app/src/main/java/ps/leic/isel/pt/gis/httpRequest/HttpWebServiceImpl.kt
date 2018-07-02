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
            dtoType: Class<T>, errorType: Class<E>, callback: (Resource<T, E>) -> Unit,
            tag: String
    ) {
        methodsMapper[method]?.let {
            val requester = Requester(it, url, body, headers, dtoType, callback, { error -> onVolleyError(error, callback, errorType) })
            RequestQueue.getInstance(applicationContext).addToRequestQueue(requester)
            requester.tag = tag
            return
        }
        callback(Resource.error("HttpWebServiceImpl does not support $method"))
    }

    override fun cancelAll(tag: String) {
        RequestQueue.getInstance(applicationContext).requestQueue.cancelAll(tag)
    }

    private fun <T, E> onVolleyError(error: VolleyError?, onError: (Resource<T, E>) -> Unit, errorType: Class<E>) {
        val resource: Resource<T, E> = when (error) {
            is ClientError -> Resource.apiError(Requester.mapper.readValue(error.networkResponse.data, errorType))
        // Unauthorized e forbidden é auth failure
            is AuthFailureError -> Resource.error("") // TODO o que fazer se as credenciais estiverem erradas ou se forem expiradas? lancar a activity para fazer login?
            is ParseError -> Resource.error("") // TODO ver o que fazer quando da erro no parse da resposta do pedido
            is NetworkError, is NoConnectionError -> Resource.error(applicationContext.getString(R.string.network_error_has_occurred))
            is ServerError, is TimeoutError -> Resource.error(applicationContext.getString(R.string.could_not_connect_to_server))
            else -> Resource.error(applicationContext.getString(R.string.unfortunately_an_error_has_occurred))
        }
        onError(resource)
    }
}