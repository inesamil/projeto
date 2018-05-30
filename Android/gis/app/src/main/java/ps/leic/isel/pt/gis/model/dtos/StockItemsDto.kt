package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class StockItemsDto(siren: Siren) {
    val stockItems: Array<StockItemDto> = siren.entities?.map {
        StockItemDto(Siren(it.klass, it.properties, null, it.actions, it.links))
    }.orEmpty().toTypedArray()
    val actions: StockItemsActions = StockItemsActions(siren.actions)
    val links: StockItemsLinks = StockItemsLinks(siren.links)

    class StockItemsActions(actions: Array<Action>?) {
        val addStockItem: Action? = actions?.find {
            it.name == addStockItemLabel
        }
    }

    class StockItemsLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(stockItemsClassLabel) ?: false
        }?.href
        val houseLink: String? = links?.find {
            it.klass?.contains(houseLabel) ?: false
        }?.href
    }

    companion object {
        const val addStockItemLabel: String = "add-stock-item"
        const val stockItemsClassLabel: String = "stock-items"
        const val houseLabel: String = "house"
    }
}