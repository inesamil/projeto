package ps.leic.isel.pt.gis.request

import android.app.Application
import com.android.volley.VolleyError

class Service<T>(private val application: Application) : WebService<T> {

    private lateinit var tag: String

    override fun fetch(method: Int, url: String, body: String?, headers: MutableMap<String, String>,
                       dtoType: Class<T>, onSuccess: (T) -> Unit, onError: (VolleyError?) -> Unit,
                       tag: String) {
        this.tag = tag
        RequestQueue.getInstance(application.applicationContext).addToRequestQueue(
                Requester(method, url, body, headers, dtoType, onSuccess, onError, tag)
        )
    }

    override fun cancel(tag: String) {
        RequestQueue.getInstance(application.applicationContext).requestQueue.cancelAll(tag)
    }
}