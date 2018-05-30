package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class ListsDto(siren: Siren) {
    val lists: Array<ListDto> = siren.entities?.map {
        ListDto(Siren(it.klass, it.properties, null, null, it.links))
    }.orEmpty().toTypedArray()
    val actions: ListsActions = ListsActions(siren.actions)
    val links: HousesLink = HousesLink(siren.links)

    class ListsActions(actions: Array<Action>?) {
        val addList: Action? = actions?.find {
            it.name == addListLabel
        }
    }

    class HousesLink(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(listsClassLabel) ?: false
        }?.href
        val houseLink: String? = links?.find {
            it.klass?.contains(listsClassLabel) ?: false
        }?.href
    }

    companion object {
        const val listsClassLabel: String = "lists"
        const val houseLabel: String = "house"
        const val addListLabel: String = "add-list"
    }
}