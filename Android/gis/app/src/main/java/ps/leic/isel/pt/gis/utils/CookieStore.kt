package ps.leic.isel.pt.gis.utils

interface CookieStore {

    fun getSessionCookie() : String?

    fun storeSessionCookie(sessionCookie: String)

    fun deleteSessionCookie()

    companion object {
        const val SESSION_COOKIE = "SESSIONID"
    }
}