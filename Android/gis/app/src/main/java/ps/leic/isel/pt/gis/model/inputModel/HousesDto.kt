package ps.leic.isel.pt.gis.model.inputModel

import ps.leic.isel.pt.gis.hypermedia.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class HousesDto(siren: Siren) {

    val houses: List<HouseDto> = siren.entities?.map {
        // TODO change null with it.links
        HouseDto(Siren(it.klass, it.properties, null, it.actions, null))
    }.orEmpty()
    val actions: HousesAction = HousesAction(siren.actions)
    val links: HousesLink = HousesLink(siren.links)

    class HousesAction(actions: Array<Action>?) {
        val addHouse: Action? = actions?.find {
            it.name == "add-house"
        }
    }

    class HousesLink(links: Array<Link>?) {
        val userLink: String? = links?.find {
            it.klass?.contains("user") ?: false
        }?.href
    }
}