package ps.leic.isel.pt.gis.model.body

import com.fasterxml.jackson.annotation.JsonProperty

data class ListProductBody(
        @field:JsonProperty(value = "product-id") val productId: Int,
        @field:JsonProperty(value = "list-product-brand") val brand: String?,
        @field:JsonProperty(value = "list-product-quantity") val quantity: Short
)