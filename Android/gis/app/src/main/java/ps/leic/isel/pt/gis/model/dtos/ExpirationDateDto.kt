package ps.leic.isel.pt.gis.model.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class ExpirationDateDto(@JsonProperty("expiration-date")
                             val expirationDate: String?,
                             @JsonProperty("quantity")
                             val quantity: Short?)