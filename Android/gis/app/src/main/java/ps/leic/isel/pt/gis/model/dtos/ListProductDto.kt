package ps.leic.isel.pt.gis.model.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class ListProductDto(
        @JsonProperty("house-id") val houseId: Long,
        @JsonProperty("list-id") val listId: Long,
        @JsonProperty("product-id") val productId: Long,
        @JsonProperty("product-name") val productName: String,
        @JsonProperty("list-product-brand") val productListBrand: String?,
        @JsonProperty("list-product-quantity") val productsListQuantity: Short
)
