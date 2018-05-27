package ps.leic.isel.pt.gis.utils

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class RequestQueue(context: Context?) {
    companion object {
        @Volatile
        private var INSTANCE: ps.leic.isel.pt.gis.utils.RequestQueue? = null

        fun getInstance(context: Context?) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: RequestQueue(context)
                }
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context?.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}
