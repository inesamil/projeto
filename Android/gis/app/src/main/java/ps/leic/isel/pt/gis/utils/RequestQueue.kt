package ps.leic.isel.pt.gis.utils

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class RequestQueue constructor(context: Context?) {
    companion object {
        @Volatile private var INSTANCE: ps.leic.isel.pt.gis.utils.RequestQueue? = null

        fun getInstance(context: Context?) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: RequestQueue(context)
                }
    }

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue,
                object : ImageLoader.ImageCache {
                    private val cache = LruCache<String, Bitmap>(20)

                    override fun getBitmap(url: String): Bitmap {
                        return cache.get(url)
                    }

                    override fun putBitmap(url: String, bitmap: Bitmap) {
                        cache.put(url, bitmap)
                    }
                })
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context?.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}
