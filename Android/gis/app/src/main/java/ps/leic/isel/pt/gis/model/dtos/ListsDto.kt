package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren
import ps.leic.isel.pt.gis.model.ActionDto

class ListsDto(siren: Siren) {
    val lists: Array<ListDto> = siren.entities?.map {
        ListDto(Siren(it.klass, it.properties, null, null, it.links))
    }.orEmpty().toTypedArray()
    val actions: ListsActions = ListsActions(siren.actions)
    val links: HousesLink = HousesLink(siren.links)

    class ListsActions(actions: Array<Action>?) {
        var addList: ActionDto? = null

        init {
            actions?.find { it.name == addListLabel }?.let {
                addList = ActionDto(it.name, it.href, it.type)
            }
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
        private const val listsClassLabel: String = "lists"
        private const val houseLabel: String = "house"
        private const val addListLabel: String = "add-list"
    }
}