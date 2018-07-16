package ps.leic.isel.pt.gis.model.body

import com.fasterxml.jackson.annotation.JsonProperty

data class InvitationBody1(
        @field:JsonProperty(value = "user-username") val username: String
)

data class InvitationBody2(
        @field:JsonProperty(value = "accept") val accept: Boolean
)