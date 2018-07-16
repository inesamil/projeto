package ps.leic.isel.pt.gis.hypermedia.jsonHome

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities.Api
import ps.leic.isel.pt.gis.hypermedia.jsonHome.subentities.ResourceObject

@JsonIgnoreProperties(ignoreUnknown = true)
class JsonHome(
        val api: Api?,
        val resources: HashMap<String, ResourceObject>)
