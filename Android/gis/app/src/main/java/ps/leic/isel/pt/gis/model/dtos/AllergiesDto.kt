package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

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
        const val allergenLabel: String = "allergy-allergen"
        const val allergyClassLabel: String = "allergy"
        const val indexLabel: String = "index"
    }
}