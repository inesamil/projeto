package ps.leic.isel.pt.gis.hypermedia.siren.subentities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Siren (@field:JsonProperty("class") val klass: Array<String>?,
             val properties: HashMap<String, Any>?, val entities: Array<Entity>?,
             val actions: Array<Action>?, val links: Array<Link>?)
