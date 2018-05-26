package ps.leic.isel.pt.gis.hypermedia.siren.subentities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Action(val name: String, val title: String?, val method: Method?, val href: String,
             val type: String?, val fields: Array<Field>?)
