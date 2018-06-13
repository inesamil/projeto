package ps.leic.isel.pt.gis.model.body

import com.fasterxml.jackson.annotation.JsonProperty

data class HouseAllergyBody(
        @field:JsonProperty(value = "allergy") val allergy: String,
        @field:JsonProperty(value = "allergics-number") val allergics: Short
)