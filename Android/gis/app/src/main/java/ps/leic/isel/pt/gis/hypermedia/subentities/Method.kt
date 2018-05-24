package ps.leic.isel.pt.gis.hypermedia.subentities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
enum class Method {
    GET, PUT, POST, DELETE, PATCH
}
