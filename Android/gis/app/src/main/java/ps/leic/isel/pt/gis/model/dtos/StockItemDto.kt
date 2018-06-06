package ps.leic.isel.pt.gis.model.dtos

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class StockItemDto(siren: Siren) {
    val houseId: Long
    val sku: String
    val productId: Long
    val productName: String
    val brand: String
    val conservationStorage: String
    val description: String
    val quantity: Short
    val variety: String
    val segment: String
    var allergens: Array<String>? = null
    var expirationDates: Array<ExpirationDateDto>? = null
    var storages: Array<String>? = null
    var movements: Array<MovementDto>? = null
    val links: StockItemLinks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        sku = properties[skuLabel] as String
        productId = (properties[productIdLabel] as Int).toLong()
        productName = properties[productNameLabel] as String
        brand = properties[brandLabel] as String
        conservationStorage = properties[conservationStorageLabel] as String
        description = properties[descriptionLabel] as String
        quantity = (properties[quantityLabel] as Int).toShort()
        variety = properties[varietyLabel] as String
        segment = properties[segmentLabel] as String
        val allergensElements = siren.entities?.find {
            it.klass?.contains(allergensLabel) ?: false
        }?.properties?.get(elementsLabel)
        allergensElements?.let {
            allergens = mapper.convertValue<Array<String>>(it, Array<String>::class.java)
        }
        val expirationDateElements = siren.entities?.find {
            it.klass?.contains(expirationDatesLabel) ?: false
        }?.properties?.get(elementsLabel)
        expirationDateElements?.let {
            expirationDates = mapper.convertValue<Array<ExpirationDateDto>>(it, Array<ExpirationDateDto>::class.java)
        }

        val storageElements = siren.entities?.find {
            it.klass?.contains(storagesLabel) ?: false
        }?.properties?.get(elementsLabel)
        storageElements?.let {
            storages = mapper.convertValue<Array<String>>(it, Array<String>::class.java)
        }

        val movementElements = siren.entities?.find {
            it.klass?.contains(movementsLabel) ?: false
        }?.properties?.get(elementsLabel)
        movementElements?.let {
            movements = mapper.convertValue<Array<MovementDto>>(it, Array<MovementDto>::class.java)
        }

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
        private const val houseIdLabel: String = "house-id"
        private const val skuLabel: String = "stock-item-sku"
        private const val productIdLabel: String = "product-id"
        private const val productNameLabel: String = "product-name"
        private const val brandLabel: String = "stock-item-brand"
        private const val conservationStorageLabel: String = "stock-item-conservation-storage"
        private const val descriptionLabel: String = "stock-item-description"
        private const val quantityLabel: String = "stock-item-quantity"
        private const val segmentLabel: String = "stock-item-segment"
        private const val varietyLabel: String = "stock-item-variety"
        private const val allergensLabel: String = "allergens"
        private const val expirationDatesLabel: String = "expiration-dates"
        private const val storagesLabel: String = "storages"
        private const val movementsLabel: String = "movements"
        private const val elementsLabel: String = "elements"
        private const val stockItemClassLabel: String = "stock-item"
        private const val stockItemsLabel: String = "stock-items"
        private val mapper: ObjectMapper = jacksonObjectMapper()
    }
}
