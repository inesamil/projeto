package ps.leic.isel.pt.gis.model.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class CharacteristicsDto(
        @field:JsonProperty(value = "babies-number") val babiesNumber: Short,
        @field:JsonProperty(value = "children-number") val childrenNumber: Short,
        @field:JsonProperty(value = "adults-number") val adultsNumber: Short,
        @field:JsonProperty(value = "seniors-number") val seniorsNumber: Short
)
