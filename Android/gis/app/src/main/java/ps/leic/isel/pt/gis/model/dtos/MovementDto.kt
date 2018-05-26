package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren
import java.time.format.DateTimeFormatter

class MovementDto(siren: Siren) {
    val houseId: Long
    val storageId: Long
    val stockItemId: Long
    val movementDateTime: String
    val movementType: String
    val quantity: Int

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        storageId = (properties?.get(storageIdLabel) as Int).toLong()
        stockItemId = (properties?.get(storageIdLabel) as Int).toLong()
        movementDateTime = properties?.get(movementDateTimeLabel) as String
        movementType = properties?.get(movementTypeLabel) as String
        quantity = properties?.get(quantityLabel) as Int
    }

    companion object {
        const val houseIdLabel: String = "house-id"
        const val storageIdLabel: String = "storage-id"
        const val stockItemId: String = "stock-item-id"
        const val movementDateTimeLabel: String = "movement-datetime"
        const val movementTypeLabel: String = "movement-type"
        const val quantityLabel: String = "movement-quantity"
    }

}
