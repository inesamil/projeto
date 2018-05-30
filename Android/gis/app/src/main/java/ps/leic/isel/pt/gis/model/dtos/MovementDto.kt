package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class MovementDto(siren: Siren) {
    val houseId: Long
    val storageId: Long
    val stockItemSku: String
    val movementDateTime: String
    val movementType: String
    val quantity: Int

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        storageId = (properties[storageIdLabel] as Int).toLong()
        stockItemSku = properties[stockItemSkuLabel] as String
        movementDateTime = properties[movementDateTimeLabel] as String
        movementType = properties[movementTypeLabel] as String
        quantity = properties[quantityLabel] as Int
    }

    companion object {
        const val houseIdLabel: String = "house-id"
        const val storageIdLabel: String = "storage-id"
        const val stockItemSkuLabel: String = "stock-item-sku"
        const val movementDateTimeLabel: String = "movement-datetime"
        const val movementTypeLabel: String = "movement-type"
        const val quantityLabel: String = "movement-quantity"
    }

}
