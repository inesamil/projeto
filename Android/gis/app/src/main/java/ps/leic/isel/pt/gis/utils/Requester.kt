package ps.leic.isel.pt.gis.utils

import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren
import java.io.IOException

open class Requester<DTO>(method: Int, url: String, body: Any?, private val headers: MutableMap<String, String>,
                          private val dtoType: Class<DTO>, onSuccess: (DTO) -> Unit,
                          onError: (VolleyError?) -> Unit, private val tag: String)
    : JsonRequest<DTO>(method, url, mapper.writeValueAsString(body), onSuccess, onError) {

    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper()
    }

    /**
     *  The function that parses the response to a DTO (generic)
     */
    override fun parseNetworkResponse(response: NetworkResponse): Response<DTO> {
        return try {
            val siren = mapper.readValue(response.data, Siren::class.java)
            val constructor = dtoType.getConstructor(Siren::class.java)
            val dto = constructor.newInstance(siren)
            setTag(tag)
            Response.success(dto, null)
        } catch (e: IOException) {
            Log.e("APP_GIS", e.message)
            Response.error(VolleyError(e))
        }
    }

    override fun getHeaders(): MutableMap<String, String> {
        return headers
    }
}