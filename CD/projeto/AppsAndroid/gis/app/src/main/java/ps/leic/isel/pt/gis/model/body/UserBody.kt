package ps.leic.isel.pt.gis.model.body

import com.fasterxml.jackson.annotation.JsonProperty

data class UserBody(
        @field:JsonProperty(value = "user-username") val username: String,
        @field:JsonProperty(value = "user-name") val name: String,
        @field:JsonProperty(value = "user-email") val email: String,
        @field:JsonProperty(value = "user-age") val age: Short,
        @field:JsonProperty(value = "user-password") val password: String
)