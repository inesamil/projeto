package ps.leic.isel.pt.gis.model.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

data class MemberDto (
    @field:JsonProperty(value = "house-id") val houseId: Long,
    @field:JsonProperty(value = "user-username") val username: String,
    @field:JsonProperty(value = "administrator") val administrator: Boolean
)
