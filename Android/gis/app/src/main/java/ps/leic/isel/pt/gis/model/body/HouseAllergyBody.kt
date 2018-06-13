package ps.leic.isel.pt.gis.model.body

import com.fasterxml.jackson.annotation.JsonProperty

data class AllergyBody(
        @field:JsonProperty(value = "allergy") val allergen: String,
        @field:JsonProperty(value = "allergics-number") val allergics: Short
)