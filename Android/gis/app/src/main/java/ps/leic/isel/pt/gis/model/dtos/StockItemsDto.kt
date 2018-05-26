package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class StockItemsDto(siren: Siren) {
    val stockItems: Array<StockItemDto>
    val actions: StockItemsActions
    val links: StockItemsLinks

    init {
        stockItems = siren.entities?.map {
            StockItemDto(Siren(it.klass, it.properties, null, it.actions, it.links))
        }.orEmpty().toTypedArray()
        actions = StockItemsActions(siren.actions)
        links = StockItemsLinks(siren.links)
    }

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