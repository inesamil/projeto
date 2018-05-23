package ps.leic.isel.pt.gis.utils

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.IOException

class Requester<DTO>(method: Int, url: String, body: Any?, private val dtoType: Class<DTO>,
                     onSuccess: (DTO) -> Unit, onError: (VolleyError) -> Unit, val tag: String)
    : JsonRequest<DTO>(method, url, Requester.mapper.writeValueAsString(body), onSuccess, onError) {

    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper()
    }

    /**
     *  The function that parses the response to a DTO (generic)
     */
    override fun parseNetworkResponse(response: NetworkResponse): Response<DTO> {
        return try {
            val dto = Requester.mapper.readValue(response.data, dtoType)
            setTag(tag)
            Response.success(dto, null)
        } catch (e: IOException) {
            Response.error(VolleyError(response))
        }
    }
}