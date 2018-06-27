package ps.leic.isel.pt.gis.utils

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.hypermedia.jsonHome.JsonHome
import ps.leic.isel.pt.gis.hypermedia.problemDetails.ProblemDetails
import ps.leic.isel.pt.gis.hypermedia.siren.Siren
import ps.leic.isel.pt.gis.repositories.Resource
import java.io.IOException


class Requester<T, E>(
        method: Int, url: String, body: Any?, private val headers: MutableMap<String, String>,
        private val dtoType: Class<T>, private val errorType: Class<E>, onSuccess: (Resource<T, E>) -> Unit,
        onError: (VolleyError?) -> Unit
) : JsonRequest<Resource<T, E>>(
        method, UrlUtils.parseUrl(url), mapper.writeValueAsString(body), onSuccess, onError
) {

    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper()
        private const val SIREN_JSON = "application/vnd.siren+json"
        private const val HOME_JSON = "application/home+json"
        private const val PROBLEM_JSON = "application/problem+json"
        private val hypermediaClasses: HashMap<String, Class<*>> = hashMapOf(
                Pair(SIREN_JSON, Siren::class.java),
                Pair(HOME_JSON, JsonHome::class.java),
                Pair(PROBLEM_JSON, ProblemDetails::class.java)
        )
    }

    /**
     *  The function that parses the response to a DTO (generic)
     */
    override fun parseNetworkResponse(response: NetworkResponse): Response<Resource<T, E>> {
        return try {
            val hypermediaType = response.headers["content-type"]
            val hypermedia = mapper.readValue(response.data, hypermediaClasses[hypermediaType])
            if (hypermediaType == PROBLEM_JSON) {
                val constructor = errorType.getConstructor(hypermediaClasses[hypermediaType])
                val errorApi = constructor.newInstance(hypermedia)
                Response.success(Resource.apiError(errorApi), null)
            } else {
                val constructor = dtoType.getConstructor(hypermediaClasses[hypermediaType])
                val dto = constructor.newInstance(hypermedia)
                Response.success(Resource.success(dto), null)
            }
        } catch (e: IOException) {
            Response.error(VolleyError(e))
        }
    }

    override fun getHeaders(): MutableMap<String, String> {
        return headers
    }
}