package ps.leic.isel.pt.gis.volley

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.hypermedia.jsonHome.JsonHome
import ps.leic.isel.pt.gis.hypermedia.siren.Siren
import ps.leic.isel.pt.gis.repositories.Resource
import ps.leic.isel.pt.gis.utils.UrlUtils
import java.io.IOException


class Requester<T, E>(
        method: Int, url: String, body: Any?, private val headers: MutableMap<String, String>,
        private val dtoType: Class<T>, onSuccess: (Resource<T, E>) -> Unit,
        onError: (VolleyError?) -> Unit
) : JsonRequest<Resource<T, E>>(
        method, UrlUtils.parseUrl(url), mapper.writeValueAsString(body), onSuccess, onError
) {

    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper()
        private const val SIREN_JSON = "application/vnd.siren+json"
        private const val HOME_JSON = "application/home+json"
        private val hypermediaClasses: HashMap<String, Class<*>> = hashMapOf(
                Pair(SIREN_JSON, Siren::class.java),
                Pair(HOME_JSON, JsonHome::class.java)
        )
    }

    /**
     *  The function that parses the response to a DTO (generic)
     */
    override fun parseNetworkResponse(response: NetworkResponse): Response<Resource<T, E>> {
        return try {
            if (response.data.isNotEmpty()) {
                val hypermediaType = response.headers["content-type"]
                val hypermedia = mapper.readValue(response.data, hypermediaClasses[hypermediaType])
                val constructor = dtoType.getConstructor(hypermediaClasses[hypermediaType])
                val dto = constructor.newInstance(hypermedia)
                return Response.success(Resource.success(dto), null)
            }
            return Response.success(Resource.success(null), null)
        } catch (e: IOException) {
            Response.error(VolleyError(e))
        }
    }

    override fun getHeaders(): MutableMap<String, String> {
        return headers
    }
}