package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class AllergiesStockItemDto(siren: Siren) {
    val allergiesStockItem: Array<String>
    val links: AllergiesStockItemLinks

    init {
        val entities = siren.entities
        allergiesStockItem = entities?.map {
            it.properties?.get(allergenLabel) as String
        }.orEmpty().toTypedArray()
        links = AllergiesStockItemLinks(siren.links)
    }

    class AllergiesStockItemLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(allergyStockItemClassLabel) ?: false
        }?.href
        val stockItemLink: String? = links?.find {
            it.klass?.contains(stockItemLabel) ?: false
        }?.href
    }

    companion object {
        const val allergenLabel: String = "allergy-allergen"
        const val allergyStockItemClassLabel: String = "allergies-stock-item"
        const val stockItemLabel: String = "stock-item"
    }
}