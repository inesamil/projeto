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
    val expirationDates: Array<ExpirationDateDto>
    val storages: Array<String>
    val movements: Array<MovementDto>
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
        quantity = properties[quantityLabel] as Short
        variety = properties[varietyLabel] as String
        segment = properties[segmentLabel] as String
        val expirationDateElements = siren.entities?.find {
            it.klass?.contains(expirationDatesLabel) ?: false
        }?.properties?.get(elementsLabel)
        expirationDates = mapper.convertValue<Array<ExpirationDateDto>>(expirationDateElements, Array<ExpirationDateDto>::class.java)

        val storageElements = siren.entities?.find {
            it.klass?.contains(storagesLabel) ?: false
        }?.properties?.get(elementsLabel)
        storages = mapper.convertValue<Array<String>>(storageElements, Array<String>::class.java)

        val movementElements = siren.entities?.find {
            it.klass?.contains(movementsLabel) ?: false
        }?.properties?.get(elementsLabel)
        movements = mapper.convertValue<Array<MovementDto>>(movementElements, Array<MovementDto>::class.java)
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
        const val productIdLabel: String = "product-id"
        const val productNameLabel: String = "product-name"
        const val brandLabel: String = "stock-item-brand"
        const val conservationStorageLabel: String = "stock-item-conservation-storage"
        const val descriptionLabel: String = "stock-item-description"
        const val quantityLabel: String = "stock-item-quantity"
        const val segmentLabel: String = "stock-item-segment"
        const val varietyLabel: String = "stock-item-variety"
        const val expirationDatesLabel: String = "expiration-dates"
        const val storagesLabel: String = "storages"
        const val movementsLabel: String = "movements"
        const val elementsLabel: String = "elements"
        const val stockItemClassLabel: String = "stock-item"
        const val stockItemsLabel: String = "stock-items"
        val mapper: ObjectMapper = jacksonObjectMapper()
    }

}