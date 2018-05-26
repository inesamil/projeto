package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class HousesDto(siren: Siren) {
    val houses: Array<HouseDto>
    val actions: HousesActions
    val links: HousesLinks

    init {
        houses = siren.entities?.map {
            HouseDto(Siren(it.klass, it.properties, null, it.actions, it.links))
        }.orEmpty().toTypedArray()
        actions = HousesActions(siren.actions)
        links = HousesLinks(siren.links)
    }

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