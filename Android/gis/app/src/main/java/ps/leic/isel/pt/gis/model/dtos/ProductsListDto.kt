package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class ProductsListDto(siren: Siren) {
    val productsList: Array<ProductListDto>
    val actions: ProductsListActions
    val links: ProductsListLink

    init {
        productsList = siren.entities?.map {
            ProductListDto(Siren(it.klass, it.properties, null, null, it.links))
        }.orEmpty().toTypedArray()
        actions = ProductsListActions(siren.actions)
        links = ProductsListLink(siren.links)
    }

    class ProductsListActions(actions: Array<Action>?) {
        val updateProduc: Action? = actions?.find {
            it.name == updateProductLabel
        }
        val deleteListProducts: Action? = actions?.find {
            it.name == deleteListProductsLabel
        }
    }

    class ProductsListLink(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(productsListClassLabel) ?: false
        }?.href
        val listLink: String? = links?.find {
            it.klass?.contains(listLabel) ?: false
        }?.href
    }

    companion object {
        const val productsListClassLabel: String = "products-list"
        const val listLabel: String = "list"
        const val updateProductLabel: String = "update-product"
        const val deleteListProductsLabel: String = "delete-list-products"
    }
}