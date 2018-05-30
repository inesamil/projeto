package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class ProductsDto(siren: Siren) {
    val products: Array<ProductDto> = siren.entities?.map {
        ProductDto(Siren(it.klass, it.properties, null, null, it.links))
    }.orEmpty().toTypedArray()
    val links: ProductsLink = ProductsLink(siren.links)

    class ProductsLink(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(productsLabel) ?: false
        }?.href
        val categoryLink: String? = links?.find {
            it.klass?.contains(categoryLabel) ?: false
        }?.href
    }

    companion object {
        const val productsLabel: String = "products"
        const val categoryLabel: String = "category"
    }
}