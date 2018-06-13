package ps.leic.isel.pt.gis.model

import com.fasterxml.jackson.annotation.JsonProperty

data class HouseAllergy(
        val allergy: String,
        var allergics: Short
)