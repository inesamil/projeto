package ps.leic.isel.pt.gis.model.body

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class HouseAllergiesBody (
        @field:JsonProperty(value = "allergies") val allergies: Array<HouseAllergyBody>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HouseAllergiesBody

        if (!Arrays.equals(allergies, other.allergies)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(allergies)
    }
}