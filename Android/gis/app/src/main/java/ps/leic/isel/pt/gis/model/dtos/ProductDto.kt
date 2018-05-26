package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class ProductDto(siren: Siren) {

    val categoryId: Long
    val productId: Long
    val productName: String
    val productEdible: Boolean
    val productShellifetime: String
    val links: ProductLinks

    init {
        val properties = siren.properties
        categoryId = (properties?.get(categoryIdLabel) as Int).toLong()
        productId = (properties?.get(productIdLabel) as Int).toLong()
        productName = properties?.get(productNameLabel) as String
        productEdible = properties?.get(productEdibleLabel) as Boolean
        productShellifetime = properties?.get(productShellifetimeLabel) as String
        links = ProductLinks(siren.links)
    }

    class ProductLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(productClassLabel) ?: false
        }?.href
        val productCategoryLink: String? = links?.find {
            it.klass?.contains(productCategoryLabel) ?: false
        }?.href
    }

    companion object {
        const val categoryIdLabel: String = "category-id"
        const val productIdLabel: String = "product-id"
        const val productNameLabel: String = "product-name"
        const val productEdibleLabel: String = "product-edible"
        const val productShellifetimeLabel: String = "product-shelflifetime"
        const val productClassLabel: String = "product"
        const val productCategoryLabel: String = "products-category"
    }
}