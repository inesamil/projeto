package ps.leic.isel.pt.gis.utils

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.GisApplication
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities.JsonHome
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren
import java.io.IOException


class Requester<DTO>(method: Int, url: String, body: Any?, private val headers: MutableMap<String, String>,
                     private val dtoType: Class<DTO>, onSuccess: (DTO) -> Unit,
                     onError: (VolleyError?) -> Unit, private val tag: String)
    : JsonRequest<DTO>(method, UrlUtils.parseUrl(url), mapper.writeValueAsString(body), onSuccess, onError) {

    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper()
        val hypermediaClasses: HashMap<String, Class<*>> = hashMapOf(
                Pair("application/vnd.siren+json", Siren::class.java),
                Pair("application/home+json", JsonHome::class.java)
        )
        val encoding: String = "UTF-8"
    }

    /**
     *  The function that parses the response to a DTO (generic)
     */
    override fun parseNetworkResponse(response: NetworkResponse): Response<DTO> {
        return try {
            val cookies = response.headers["set-cookie"]


            val hypermediaType = response.headers["content-type"]
            val hypermedia = mapper.readValue(response.data, hypermediaClasses[hypermediaType])
            val constructor = dtoType.getConstructor(hypermediaClasses[hypermediaType])
            val dto = constructor.newInstance(hypermedia)
            setTag(tag)
            Response.success(dto, null)
        } catch (e: IOException) {
            Response.error(VolleyError(e))
        }
    }

    override fun getHeaders(): MutableMap<String, String> {
        return headers
    }
}