package ps.leic.isel.pt.hardwaresimulator

import com.fasterxml.jackson.annotation.JsonProperty

data class MovementDto(val tag: String, @JsonProperty("storage-id") val storageId: Short,
                       @JsonProperty("movement-type") val type: Boolean, val quantity: Short)