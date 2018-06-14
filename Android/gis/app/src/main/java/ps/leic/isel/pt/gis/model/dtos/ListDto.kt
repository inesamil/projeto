package ps.leic.isel.pt.gis.model.dtos

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren
import ps.leic.isel.pt.gis.model.ActionDto

class ListDto(siren: Siren) {
    val houseId: Long
    val houseName: String
    val listId: Short
    val listName: String
    val listType: String
    var username: String? = null
    var shareable: Boolean? = null
    var listProducts: Array<ListProductDto>? = null
    val actions: HousesActions
    val links: HouseLinks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        houseName = properties[houseNameLabel] as String
        listId = (properties[listIdLabel] as Int).toShort()
        listName = properties[listNameLabel] as String
        listType = properties[listTypeLabel] as String
        username = properties[usernameLabel] as? String
        shareable = properties[shareableLabel] as? Boolean

        val listProductsElements = siren.entities?.find {
            it.klass?.contains(listProductsLabel) ?: false
        }?.properties?.get(elementsLabel)
        listProducts = mapper.convertValue<Array<ListProductDto>>(listProductsElements, Array<ListProductDto>::class.java)

        actions = HousesActions(siren.actions)
        links = HouseLinks(siren.links)
    }

    class HousesActions(actions: Array<Action>?) {
        var updateListProducts: ActionDto? = null
        var updateList: ActionDto? = null
        var deleteList: ActionDto? = null

        init {
            actions?.find {
                it.name == updateListProductsLabel
            }?.let { updateListProducts = ActionDto(it.name, it.href, it.type) }

            actions?.find {
                it.name == updateListLabel
            }?.let { updateList = ActionDto(it.name, it.href, it.type) }

            actions?.find {
                it.name == deleteListLabel
            }?.let { deleteList = ActionDto(it.name, it.href, it.type) }
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
        const val SYSTEM_TYPE = "system"
        const val USER_TYPE = "user"
        private val mapper: ObjectMapper = jacksonObjectMapper()
        private const val houseIdLabel: String = "house-id"
        private const val houseNameLabel: String = "house-name"
        private const val listIdLabel: String = "list-id"
        private const val listNameLabel = "list-name"
        private const val listTypeLabel = "list-type"
        private const val usernameLabel: String = "user-username"
        private const val shareableLabel: String = "list-shareable"
        private const val listProductsLabel: String = "list-products"
        private const val elementsLabel: String = "elements"
        private const val listClassLabel: String = "list"
        private const val listsLabel: String = "lists"
        private const val updateListLabel: String = "update-list"
        private const val updateListProductsLabel: String = "update-list-product"
        private const val deleteListLabel: String = "delete-list"
    }
}