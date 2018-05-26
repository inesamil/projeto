package ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities

import com.fasterxml.jackson.annotation.JsonInclude


@JsonInclude(JsonInclude.Include.NON_NULL)
class ResourceObject(val href: String, val hrefTemplate: String, val hrefVars: HashMap<String, String>, val hints: Hints)