package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class CategoryDto(siren: Siren) {
    val categoryId: Long
    val categoryName: String
    val links: CategoryLinks

    init {
        val properties = siren.properties
        categoryId = (properties?.get(categoryIdLabel) as Int).toLong()
        categoryName = properties[categoryNameLabel] as String
        links = CategoryLinks(siren.links)
    }

    class CategoryLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(categoryClassLabel) ?: false
        }?.href
        val productsCategoryLink: String? = links?.find {
            it.klass?.contains(productsCategoryLabel) ?: false
        }?.href
    }

    companion object {
        private const val categoryIdLabel: String = "category-id"
        private const val categoryNameLabel: String = "category-name"
        private const val categoryClassLabel: String = "category"
        private const val productsCategoryLabel: String = "products-category"
    }
}
