package ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class Hints
(val allow: Array<Method>, val formats: HashMap<String, Any>,
 val acceptPost: Array<String>?, val acceptPut: Array<String>?,
 val authSchemes: Array<HashMap<String, Any>>?)