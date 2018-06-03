package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class AllergiesDto(siren: Siren) {
    val allergies: Array<String>
    val links: AllergyLinks

    init {
        val entities = siren.entities
        allergies = entities?.map {
            it.properties?.get(allergenLabel) as String
        }.orEmpty().toTypedArray()
        links = AllergyLinks(siren.links)
    }

    class AllergyLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(allergyClassLabel) ?: false
        }?.href
        val indexLink: String? = links?.find {
            it.klass?.contains(indexLabel) ?: false
        }?.href
    }

    companion object {
        private const val allergenLabel: String = "allergy-allergen"
        private const val allergyClassLabel: String = "allergy"
        private const val indexLabel: String = "index"
    }
}