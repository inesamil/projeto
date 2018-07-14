package ps.leic.isel.pt.gis.utils

import android.util.Base64

object HeadersUtils {

    /**
     * AUTHORIZATION HEADER
     */
    private const val AUTHORIZATION: String = "Authorization"

    fun getAuthorizationHeader(headers: Map<String, String>): String? {
        return headers[AUTHORIZATION]
    }

    fun setAuthorizationHeader(headers: MutableMap<String, String>, username: String, password: String) {
        val encodedUsernamePassword = Base64.encodeToString("$username:$password".toByteArray(), Base64.DEFAULT)
        headers[AUTHORIZATION] = "Basic $encodedUsernamePassword"
    }

    /**
     * CONTENT-TYPE HEADER
     */
    private const val CONTENT_TYPE: String = "Content-Type"

    fun getContentTypeHeader(headers: Map<String, String>): String? {
        return headers[CONTENT_TYPE]
    }

    fun setContentTypeHeader(headers: MutableMap<String, String>, contentType: String?) {
        contentType?.let {
            headers[CONTENT_TYPE] = it
        }
    }

    /**
     * ACCEPT-LANGUAGE HEADER
     */
    private const val ACCEPT_LANGUAGE: String = "Accept-Language"

    fun getAcceptLanguageHeader(headers: Map<String, String>): String? {
        return headers[ACCEPT_LANGUAGE]
    }

    fun setAcceptLanguageHeader(headers: MutableMap<String, String>, acceptLanguage: String?) {
        acceptLanguage?.let {
            headers[ACCEPT_LANGUAGE] = it
        }
    }
}