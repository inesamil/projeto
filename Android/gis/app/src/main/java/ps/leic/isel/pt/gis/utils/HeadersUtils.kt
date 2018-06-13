package ps.leic.isel.pt.gis.utils

import android.util.Base64

object HeadersUtils {

    fun getAuthorizationHeader(username: String, password: String) : String {
        val encodedUsernamePassword = Base64.encodeToString("$username:$password".toByteArray(), Base64.DEFAULT)
        return "Basic $encodedUsernamePassword"
    }
}