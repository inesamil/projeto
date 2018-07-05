package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.Siren
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link

class ListDto(siren: Siren) {
    val houseId: Long
    val houseName: String
    val listId: Short
    val listName: String
    val listType: String
    var username: String? = null
    var shareable: Boolean? = null
    var listProducts: Array<ListProductDto> = siren.entities?.map {
        ListProductDto(Siren(it.klass, it.properties, null, it.actions, it.links))
    }.orEmpty().toTypedArray()
    val actions: ListActions
    val links: ListLinks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        houseName = properties[houseNameLabel] as String
        listId = (properties[listIdLabel] as Int).toShort()
        listName = properties[listNameLabel] as String
        listType = properties[listTypeLabel] as String
        username = properties[usernameLabel] as? String
        shareable = properties[shareableLabel] as? Boolean
        actions = ListActions(siren.actions)
        links = ListLinks(siren.links)
    }

    class ListActions(actions: Array<Action>?) {
        var addListProduct: ActionDto? = null
        var updateList: ActionDto? = null
        var deleteList: ActionDto? = null

        init {
            actions?.find {
                it.name == addListProductLabel
            }?.let { addListProduct = ActionDto(it.name, it.href, it.type) }

            actions?.find {
                it.name == updateListLabel
            }?.let { updateList = ActionDto(it.name, it.href, it.type) }

            actions?.find {
                it.name == deleteListLabel
            }?.let { deleteList = ActionDto(it.name, it.href, it.type) }
        }
    }

    class ListLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(listClassLabel) ?: false
        }?.href
        val listsLink: String? = links?.find {
            it.klass?.contains(listsLabel) ?: false
        }?.href
    }

    companion object {
        const val SYSTEM_TYPE = "system"
        const val USER_TYPE = "user"
        private const val houseIdLabel: String = "house-id"
        private const val houseNameLabel: String = "house-name"
        private const val listIdLabel: String = "list-id"
        private const val listNameLabel = "list-name"
        private const val listTypeLabel = "list-type"
        private const val usernameLabel: String = "user-username"
        private const val shareableLabel: String = "list-shareable"
        private const val listClassLabel: String = "list"
        private const val listsLabel: String = "lists"
        private const val addListProductLabel: String = "add-list-product"
        private const val updateListLabel: String = "update-list"
        private const val deleteListLabel: String = "delete-list"
    }
}