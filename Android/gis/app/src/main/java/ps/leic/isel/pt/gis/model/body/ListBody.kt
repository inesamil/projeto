package ps.leic.isel.pt.gis.model.body

import com.fasterxml.jackson.annotation.JsonProperty

data class ListBody(
        @field:JsonProperty(value = "house-id") val houseId: Long,
        @field:JsonProperty(value = "list-name") val listName: String,
        @field:JsonProperty(value = "list-shareable") val listShareable: Boolean
)