package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class ListProductDto(siren: Siren) {
    val houseId: Long
    val listId: Long
    val productId: Long
    val productName: String
    val productListBrand: String
    val productsListQuantity: Short
    val links: ProductListLynks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        listId = (properties[listIdLabel] as Int).toLong()
        productId = (properties[productIdLabel] as Int).toLong()
        productName = properties[productNameLabel] as String
        productListBrand = properties[productListBrandLabel] as String
        productsListQuantity = properties[productsListQuantityLabel] as Short
        links = ProductListLynks(siren.links)
    }

    class ProductListLynks(links: Array<Link>?) {
        val productLink: String? = links?.find {
            it.klass?.contains(productLabel) ?: false
        }?.href
    }

    companion object {
        const val houseIdLabel: String = "house-id"
        const val listIdLabel: String = "list-id"
        const val productIdLabel: String = "product-id"
        const val productNameLabel: String = "product-name"
        const val productListBrandLabel: String = "list-product-brand"
        const val productsListQuantityLabel: String = "list-product-quantity"
        const val productLabel: String = "product"
    }

}
