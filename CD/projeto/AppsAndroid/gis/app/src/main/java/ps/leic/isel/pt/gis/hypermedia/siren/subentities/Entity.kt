package ps.leic.isel.pt.gis.hypermedia.siren.subentities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class Entity(@field:JsonProperty(value = "class") var klass: Array<String>?, var rel: Array<String>,
             var properties: HashMap<String, Any>?, var actions: Array<Action>?, var links: Array<Link>?)
