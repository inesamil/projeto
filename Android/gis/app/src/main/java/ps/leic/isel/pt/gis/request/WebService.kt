package ps.leic.isel.pt.gis.request

import com.android.volley.VolleyError

interface WebService<T> {

    fun fetch(method: Int, url: String, body: String?, headers: MutableMap<String, String>, dtoType: Class<T>,
              onSuccess: (T) -> Unit, onError: (VolleyError?) -> Unit, tag: String)

    fun cancel(tag: String)
}