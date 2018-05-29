package ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Api(val title: String?, val links: Links?)
