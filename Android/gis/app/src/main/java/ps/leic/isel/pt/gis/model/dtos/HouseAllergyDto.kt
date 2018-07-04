package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.Siren
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link

class HouseAllergyDto(siren: Siren) {
    val houseId: Long
    val allergen: String
    val houseAllergiesNum: Short
    val actions: HouseAllergyActions
    val links: HouseAllergyLynks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        allergen = properties[allergenLabel] as String
        houseAllergiesNum = (properties[houseAllergiesNumLabel] as Int).toShort()
        actions = HouseAllergyActions(siren.actions)
        links = HouseAllergyLynks(siren.links)
    }

    class HouseAllergyActions(actions: Array<Action>?) {
        val updateHouseAllergy: Action? = actions?.find {
            it.name == updateHouseAllergyLabel
        }
        val deleteHouseAllergy: Action? = actions?.find {
            it.name == deleteHouseAllergyLabel
        }
    }

    class HouseAllergyLynks(links: Array<Link>?) {
        val stockItemAllergenLink: String? = links?.find {
            it.klass?.contains(stockItemAllergenLabel) ?: false
        }?.href
    }

    companion object {
        private const val houseIdLabel: String = "house-id"
        private const val allergenLabel: String = "allergy-allergen"
        private const val houseAllergiesNumLabel: String = "allergics-number"
        private const val stockItemAllergenLabel: String = "stock-items-allergen"
        private const val updateHouseAllergyLabel: String = "update-house-allergy"
        private const val deleteHouseAllergyLabel: String = "delete-house-allergy"
    }
}
