package ps.leic.isel.pt.gis.model.dtos

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class ListDto(siren: Siren) {
    val houseId: Long
    val listId: Long
    val listName: String
    var username: String? = null
    var shareable: Boolean? = null
    var listProducts: Array<ListProductDto>? = null
    val actions: HousesActions
    val links: HouseLinks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        listId = (properties[listIdLabel] as Int).toLong()
        listName = properties[listNameLabel] as String
        username = properties[usernameLabel] as String
        shareable = properties[shareableLabel] as Boolean

        val listProductsElements = siren.entities?.find {
            it.klass?.contains(listProductsLabel) ?: false
        }?.properties?.get(elementsLabel)
        listProducts = mapper.convertValue<Array<ListProductDto>>(listProductsElements, Array<ListProductDto>::class.java)

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
        private val mapper: ObjectMapper = jacksonObjectMapper()
        private const val houseIdLabel: String = "house-id"
        private const val listIdLabel: String = "list-id"
        private const val listNameLabel = "list-name"
        private const val usernameLabel: String = "username"
        private const val shareableLabel: String = "shareable"
        private const val listProductsLabel: String = "list-products"
        private const val elementsLabel: String = "elements"
        private const val listClassLabel: String = "list"
        private const val listsLabel: String = "lists"
        private const val updateListLabel: String = "update-list"
        private const val deleteListLabel: String = "delete-list"
    }
}