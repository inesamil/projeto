package ps.leic.isel.pt.hardwaresimulator

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.IOException

class PostRequest(method: Int, url: String, body: Any?, onSuccess: (String) -> Unit, onError: (VolleyError?) -> Unit)
    : JsonRequest<String>(method, url, mapper.writeValueAsString(body), onSuccess, onError) {

    override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
        return try {
            Response.success(response?.statusCode.toString(), null)
        } catch (e: IOException) {
            Response.error(VolleyError(e))
        }
    }

    override fun getHeaders(): MutableMap<String, String> {
        val headers = hashMapOf<String, String>()
        headers["content-type"] = "application/json"
        return headers
    }

    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper()
    }
}