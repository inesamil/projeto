package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

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
        houseAllergiesNum = properties[houseAllergiesNumLabel] as Short
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
        const val houseIdLabel: String = "house-id"
        const val allergenLabel: String = "allergy-allergen"
        const val houseAllergiesNumLabel: String = "house-allergies-Num"
        const val stockItemAllergenLabel: String = "stock-items-allergen"
        const val updateHouseAllergyLabel: String = "update-house-allergy"
        const val deleteHouseAllergyLabel: String = "delete-house-allergy"
    }
}
