package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class ProductDto(siren: Siren) {

    val categoryId: Int
    val productId: Int
    val productName: String
    val productEdible: Boolean
    val productShellifetime: String
    val links: ProductLinks

    init {
        val properties = siren.properties
        categoryId = (properties?.get(categoryIdLabel) as Int)
        productId = (properties[productIdLabel] as Int)
        productName = properties[productNameLabel] as String
        productEdible = properties[productEdibleLabel] as Boolean
        productShellifetime = properties[productShellifetimeLabel] as String
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
        private const val categoryIdLabel: String = "category-id"
        private const val productIdLabel: String = "product-id"
        private const val productNameLabel: String = "product-name"
        private const val productEdibleLabel: String = "product-edible"
        private const val productShellifetimeLabel: String = "product-shelflifetime"
        private const val productClassLabel: String = "product"
        private const val productCategoryLabel: String = "products-category"
    }
}