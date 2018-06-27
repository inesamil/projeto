package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.Siren

class CategoriesDto(siren: Siren) {
    val categories: Array<CategoryDto>
    val links: CategoriesLinks

    init {
        val entities = siren.entities
        categories = entities?.map {
            CategoryDto(Siren(it.klass, it.properties, null, null, it.links))
        }.orEmpty().toTypedArray()
        links = CategoriesLinks(siren.links)
    }

    class CategoriesLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(categoriesClassLabel) ?: false
        }?.href
        val indexLink: String? = links?.find {
            it.klass?.contains(indexLabel) ?: false
        }?.href
    }

    companion object {
        private const val categoriesClassLabel: String = "categories"
        private const val indexLabel: String = "index"
    }
}
