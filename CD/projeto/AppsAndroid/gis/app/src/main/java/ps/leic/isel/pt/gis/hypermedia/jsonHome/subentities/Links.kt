package ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class Links(val author: String?, val describedBy: String?, val license: String?)