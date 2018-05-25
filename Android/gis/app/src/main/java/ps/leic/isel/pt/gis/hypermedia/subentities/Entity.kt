package ps.leic.isel.pt.gis.hypermedia.subentities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
class Entity(@field:JsonProperty(value = "class") var klass: Array<String>?, var rel: Array<String>,
             var properties: HashMap<String, Any>?, var actions: Array<Action>?, var links: Array<Link>)
