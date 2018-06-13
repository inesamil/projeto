package ps.leic.isel.pt.gis.utils

object CookieUtils {

    private const val NAME_IDX = 0
    private const val VALUE_IDX = 1
    private const val PAIR_SEPARATOR = "="
    private const val PAIRS_DELIMITER = "; "

    fun getSessionCookieFromCookie(cookie: String) : String? {
        var sessionIdValue: String? = null
        if (cookie.isNotEmpty()) {
            val splitCookie = cookie.split(PAIRS_DELIMITER)
            sessionIdValue = splitCookie.find { pair -> pair.split(PAIR_SEPARATOR)[NAME_IDX] == CookieStore.SESSION_COOKIE }
                    ?.split(PAIR_SEPARATOR)?.get(VALUE_IDX)
        }
        return sessionIdValue
    }

    fun getCookieString(cookies: List<Pair<String, String>>) : String? {
        if (cookies.isEmpty())
            return null
        val stringBuilder = StringBuilder()
        stringBuilder.append("${cookies[0].first}${PAIR_SEPARATOR}${cookies[0].second}")
        for (i in 1 until cookies.size) {
            stringBuilder.append("$PAIRS_DELIMITER${cookies[i].first}$PAIR_SEPARATOR${cookies[i].second}")
        }
        return stringBuilder.toString()
    }
}