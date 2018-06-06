package ps.leic.isel.pt.gis.model.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

data class MovementDto (
        @field:JsonProperty(value = "house-id") val houseId: Long,
        @field:JsonProperty(value = "storage-id") val storageId: Short,
        @field:JsonProperty(value = "stock-item-sku") val stockItemSku: String,
        @field:JsonProperty(value = "movement-datetime") val movementDateTime: String,
        @field:JsonProperty(value = "movement-type") val movementType: Boolean,
        @field:JsonProperty(value = "movement-quantity") val quantity: Int
)
