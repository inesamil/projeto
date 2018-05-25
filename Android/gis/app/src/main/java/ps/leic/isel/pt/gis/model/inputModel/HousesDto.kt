package ps.leic.isel.pt.gis.model.inputModel

import ps.leic.isel.pt.gis.hypermedia.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class HousesDto(siren: Siren) {
    val houses: Array<HouseDto>
    val actions: HousesActions
    val links: HousesLink

    init {
        houses = siren.entities?.map {
            HouseDto(Siren(it.klass, it.properties, null, it.actions, it.links))
        }.orEmpty().toTypedArray()
        actions = HousesActions(siren.actions)
        links = HousesLink(siren.links)
    }

    class HousesActions(actions: Array<Action>?) {
        val addHouse: Action? = actions?.find {
            it.name == addHouseLabel
        }
    }

    class HousesLink(links: Array<Link>?) {
        val userLink: String? = links?.find {
            it.klass?.contains(userLabel) ?: false
        }?.href
    }

    companion object {
        const val userLabel: String = "user"
        const val addHouseLabel: String = "add-house"
    }
}