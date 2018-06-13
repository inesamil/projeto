package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren
import ps.leic.isel.pt.gis.model.ActionDto

class HouseAllergiesDto(siren: Siren) {
    val houseAllergies: Array<HouseAllergyDto>
    val actions: HouseAllergiesActions
    val links: HouseAllergiesLinks

    init {
        val entities = siren.entities
        houseAllergies = entities?.map {
            HouseAllergyDto(Siren(it.klass, it.properties, null, it.actions, it.links))
        }.orEmpty().toTypedArray()
        actions = HouseAllergiesActions(siren.actions)
        links = HouseAllergiesLinks(siren.links)
    }

    class HouseAllergiesActions(actions: Array<Action>?) {
        var updateHouseAllergies: ActionDto? = null
        var deleteHouseAllergies: ActionDto? = null

        init {
            actions?.find { it.name == updateHouseAllergiesLabel }?.let {
                updateHouseAllergies = ActionDto(it.name, it.href, it.type)
            }
            actions?.find { it.name == deleteHouseAllergiesLabel }?.let {
                deleteHouseAllergies = ActionDto(it.name, it.href, it.type)
            }
        }
    }

    class HouseAllergiesLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(houseAllergiesClassLabel) ?: false
        }?.href
        val houseLink: String? = links?.find {
            it.klass?.contains(houseLabel) ?: false
        }?.href
    }

    companion object {
        private const val houseAllergiesClassLabel: String = "house-allergie"
        private const val houseLabel: String = "house"
        private const val updateHouseAllergiesLabel: String = "update-house-allergies"
        private const val deleteHouseAllergiesLabel: String = "delete-house-allergies"
    }

}