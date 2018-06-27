package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.Siren

class StockItemsAllergenDto(siren: Siren) {
    val stockItems: Array<StockItemDto> = siren.entities?.map {
        StockItemDto(Siren(it.klass, it.properties, null, it.actions, it.links))
    }.orEmpty().toTypedArray()
    val links: StockItemsLinks = StockItemsLinks(siren.links)

    class StockItemsLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(stockItemsClassLabel) ?: false
        }?.href
        val houseAllergiesLink: String? = links?.find {
            it.klass?.contains(houseAllergiesLabel) ?: false
        }?.href
    }

    companion object {
        private const val stockItemsClassLabel: String = "stock-items"
        private const val houseAllergiesLabel: String = "house-allergies"
    }
}