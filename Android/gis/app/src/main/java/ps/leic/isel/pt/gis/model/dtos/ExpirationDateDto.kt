package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class ExpirationDateDto(siren: Siren) {
    val expirationDate: String
    val quantity: Short

    init {
        val properties = siren.properties
        expirationDate = properties?.get(expirationDateLabel) as String
        quantity = properties?.get(quantityLabel) as Short
    }

    companion object {
        const val expirationDateLabel = "expiration-date"
        const val quantityLabel = "quantity"
    }
}