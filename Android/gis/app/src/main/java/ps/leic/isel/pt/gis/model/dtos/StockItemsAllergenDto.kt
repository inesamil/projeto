package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class StockItemsAllergenDto(siren: Siren) {
    val stockItems: Array<StockItemDto>
    val links: StockItemsLinks

    init {
        stockItems = siren.entities?.map {
            StockItemDto(Siren(it.klass, it.properties, null, it.actions, it.links))
        }.orEmpty().toTypedArray()
        links = StockItemsLinks(siren.links)
    }

    class StockItemsLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(stockItemsClassLabel) ?: false
        }?.href
        val houseAllergiesLink: String? = links?.find {
            it.klass?.contains(houseAllergiesLabel) ?: false
        }?.href
    }

    companion object {
        const val stockItemsClassLabel: String = "stock-items"
        const val houseAllergiesLabel: String = "house-allergies"
    }
}