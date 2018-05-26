package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class StockItemDto(siren: Siren) {
    val houseId: Long
    val sku: String
    val categoryId: Long
    val productId: Long
    val brand: String
    val conservationStorage: String
    val description: String
    val quantity: Short
    val segment: String
    val links: StockItemLinks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        sku = properties?.get(skuLabel) as String
        categoryId = (properties?.get(categoryIdLabel) as Int).toLong()
        productId = (properties?.get(productIdLabel) as Int).toLong()
        brand = properties?.get(brandLabel) as String
        conservationStorage = properties?.get(conservationStorageLabel) as String
        description = properties?.get(descriptionLabel) as String
        quantity = properties?.get(quantityLabel) as Short
        segment = properties?.get(segmentLabel) as String
        links = StockItemLinks(siren.links)
    }

    class StockItemLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(stockItemClassLabel) ?: false
        }?.href
        val stockItemsLink: String? = links?.find {
            it.klass?.contains(stockItemsLabel) ?: false
        }?.href
    }

    companion object {
        const val houseIdLabel: String = "house-id"
        const val skuLabel: String = "stock-item-sku"
        const val categoryIdLabel: String = "category-id"
        const val productIdLabel: String = "product-id"
        const val brandLabel: String = "stock-item-brand"
        const val conservationStorageLabel: String = "stock-item-conservation-storage"
        const val descriptionLabel: String = "stock-item-description"
        const val quantityLabel: String = "stock-item-quantity"
        const val segmentLabel: String = "stock-item-segment"
        const val variety: String = "stock-item-variety"
        const val stockItemClassLabel: String = "stock-item"
        const val stockItemsLabel: String = "stock-items"
    }

}