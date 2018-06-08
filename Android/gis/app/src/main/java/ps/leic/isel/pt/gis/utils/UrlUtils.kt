package ps.leic.isel.pt.gis.utils

import java.net.URI
import java.net.URL

object UrlUtils {

    fun parseUrl(url: String) : String {
        val urlParser = URL(url)
        val uri = URI(urlParser.protocol, urlParser.userInfo, urlParser.host, urlParser.port, urlParser.path, urlParser.query, urlParser.ref)
        return uri.toURL().toString()
    }
}