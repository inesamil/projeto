package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.model.ActionDto

class ListProductDto(siren: Siren) {
    val houseId: Long
    val listId: Short
    val productId: Int
    val productName: String
    val productListBrand: String?
    val productsListQuantity: Short
    val actions: ListProductActions

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        listId = (properties[listIdLabel] as Int).toShort()
        productId = properties[productIdLabel] as Int
        productName = properties[productNameLabel] as String
        productListBrand = properties[listProductBrandLabel] as? String
        productsListQuantity = (properties[listProductQuantityLabel] as Int).toShort()
        actions = ListProductActions(siren.actions)
    }


    class ListProductActions (actions: Array<Action>?) {
        var updateListProduct: ActionDto? = null

        init {
            actions?.find {
                it.name == updateListProductLabel
            }?.let { updateListProduct = ActionDto(it.name, it.href, it.type) }
        }
    }

    companion object {
        private const val houseIdLabel: String = "house-id"
        private const val listIdLabel: String = "list-id"
        private const val productIdLabel = "product-id"
        private const val productNameLabel = "product-name"
        private const val listProductBrandLabel: String = "list-product-brand"
        private const val listProductQuantityLabel: String = "list-product-quantity"
        private const val updateListProductLabel: String = "update-list-product"
    }
}
