package ps.leic.isel.pt.gis.hypermedia.siren.subentities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Link(var rel: Array<String>, @field:JsonProperty("class") var klass: Array<String>?,
           var href: String)
