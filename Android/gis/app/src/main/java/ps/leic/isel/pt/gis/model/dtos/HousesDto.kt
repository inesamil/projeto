package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.Siren
import ps.leic.isel.pt.gis.model.ActionDto

class HousesDto(siren: Siren) {
    val houses: Array<HouseDto> = siren.entities?.map {
        HouseDto(Siren(it.klass, it.properties, null, it.actions, it.links))
    }.orEmpty().toTypedArray()
    val actions: HousesActions = HousesActions(siren.actions)
    val links: HousesLinks = HousesLinks(siren.links)

    class HousesActions(actions: Array<Action>?) {
        var addHouse: ActionDto? = null

        init {
            actions?.find { it.name == addHouseLabel }?.let {
                addHouse = ActionDto(it.name, it.href, it.type)
            }
        }
    }

    class HousesLinks(links: Array<Link>?) {
        val userLink: String? = links?.find {
            it.klass?.contains(userLabel) ?: false
        }?.href
    }

    companion object {
        private const val userLabel: String = "user"
        private const val addHouseLabel: String = "add-house"
    }
}