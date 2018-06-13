package ps.leic.isel.pt.gis.utils

import android.util.Base64

object HeadersUtils {

    /**
     * AUTHORIZATION HEADER
     */
    const val AUTHORIZATION: String = "Authorization"

    fun getAuthorizationHeader(headers: Map<String, String>) : String? {
        return headers[AUTHORIZATION]
    }

    fun setAuthorizationHeader(headers: MutableMap<String, String>, username: String, password: String) {
        val encodedUsernamePassword = Base64.encodeToString("$username:$password".toByteArray(), Base64.DEFAULT)
        headers[AUTHORIZATION] = "Basic $encodedUsernamePassword"
    }

    /**
     * COOKIE HEADER
     */
    const val COOKIE: String = "Cookie"

    fun getCookieHeader(headers: Map<String, String>) : String? {
        return headers[COOKIE]
    }

    fun setCookieHeader(headers: MutableMap<String, String>, cookies: List<Pair<String, String>>) {
        CookieUtils.getCookieString(cookies)?.let {
            headers[COOKIE] = it
        }
    }

    /**
     * CONTENT-TYPE HEADER
     */
    const val CONTENT_TYPE: String = "Content-Type"

    fun getContentTypeHeader(headers: Map<String, String>) : String? {
        return headers[CONTENT_TYPE]
    }

    fun setContentTypeHeader(headers: MutableMap<String, String>, contentType: String?) {
        contentType?.let {
            headers[CONTENT_TYPE] = it
        }
    }
}