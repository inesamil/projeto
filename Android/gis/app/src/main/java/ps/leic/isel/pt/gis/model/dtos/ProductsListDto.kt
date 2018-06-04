package ps.leic.isel.pt.gis.model.dtos

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class ProductsListDto(siren: Siren) {
    val productsListProduct: Array<ListProductDto>
    val actions: ProductsListActions = ProductsListActions(siren.actions)
    val links: ProductsListLink = ProductsListLink(siren.links)

    init {
        val elements = siren.entities?.find {
            it.klass?.contains(listProductsEntityLabel) ?: false
        }?.properties?.get("elements")
        productsListProduct = mapper.convertValue<Array<ListProductDto>>(elements, Array<ListProductDto>::class.java)
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
        private const val productsListClassLabel: String = "products-list"
        private const val listLabel: String = "list"
        private const val updateProductLabel: String = "update-product"
        private const val deleteListProductsLabel: String = "delete-list-products"
        private const val listProductsEntityLabel: String = "list-products"
        private val mapper: ObjectMapper = jacksonObjectMapper()
    }
}