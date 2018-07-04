package ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
enum class Method {
    GET, PUT, POST, DELETE, PATCH
}