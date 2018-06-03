package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class HouseAllergiesDto(siren: Siren) {
    val houseAllergies: Array<HouseAllergyDto>
    val links: HouseAllergiesLinks

    init {
        val entities = siren.entities
        houseAllergies = entities?.map {
            HouseAllergyDto(Siren(it.klass, it.properties, null, it.actions, it.links))
        }.orEmpty().toTypedArray()
        links = HouseAllergiesLinks(siren.links)
    }

    class HouseAllergiesLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(houseAllergiesClassLabel) ?: false
        }?.href
        val houseLink: String? = links?.find {
            it.klass?.contains(houseLabel) ?: false
        }?.href
    }

    companion object {
        private const val houseAllergiesClassLabel: String = "house-allergie"
        private const val houseLabel: String = "house"
    }

}