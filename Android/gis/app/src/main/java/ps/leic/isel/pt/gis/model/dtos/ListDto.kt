package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class ListDto(siren: Siren) {
    val houseId: Long
    val listId: Long
    val listName: String
    var username: String? = null
    var shareable: Boolean? = null
    val actions: HousesActions
    val links: HouseLinks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        listId = (properties?.get(listIdLabel) as Int).toLong()
        listName = properties?.get(listNameLabel) as String
        username = properties?.get(usernameLabel) as String
        shareable = properties?.get(shareableLabel) as Boolean
        actions = HousesActions(siren.actions)
        links = HouseLinks(siren.links)
    }

    class HousesActions(actions: Array<Action>?) {
        val updateList: Action? = actions?.find {
            it.name == updateListLabel
        }
        val deleteList: Action? = actions?.find {
            it.name == deleteListLabel
        }
    }

    class HouseLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(listClassLabel) ?: false
        }?.href
        val listsLink: String? = links?.find {
            it.klass?.contains(listsLabel) ?: false
        }?.href
    }

    companion object {
        const val houseIdLabel: String = "house-id"
        const val listIdLabel: String = "list-id"
        const val listNameLabel = "list-name"
        const val usernameLabel: String = "username"
        const val shareableLabel: String = "shareable"
        const val listClassLabel: String = "list"
        const val listsLabel: String = "lists"
        const val updateListLabel: String = "update-list"
        const val deleteListLabel: String = "delete-list"
    }
}