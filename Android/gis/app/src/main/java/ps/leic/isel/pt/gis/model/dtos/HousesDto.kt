package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class HousesDto(siren: Siren) {
    val houses: Array<HouseDto> = siren.entities?.map {
        HouseDto(Siren(it.klass, it.properties, null, it.actions, it.links))
    }.orEmpty().toTypedArray()
    val actions: HousesActions = HousesActions(siren.actions)
    val links: HousesLinks = HousesLinks(siren.links)

    class HousesActions(actions: Array<Action>?) {
        val addHouse: Action? = actions?.find {
            it.name == addHouseLabel
        }
    }

    class HousesLinks(links: Array<Link>?) {
        val userLink: String? = links?.find {
            it.klass?.contains(userLabel) ?: false
        }?.href
    }

    companion object {
        const val userLabel: String = "user"
        const val addHouseLabel: String = "add-house"
    }
}