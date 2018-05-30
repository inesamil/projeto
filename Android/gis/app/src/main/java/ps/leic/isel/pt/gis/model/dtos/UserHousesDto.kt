package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class UserHousesDto(siren: Siren) {
    val houses: Array<HouseDto> = siren.entities?.map {
        HouseDto(Siren(it.klass, it.properties, null, it.actions, it.links))
    }.orEmpty().toTypedArray()
    val links: UserHousesLinks = UserHousesLinks(siren.links)

    class UserHousesLinks(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(userHousesClassLabel) ?: false
        }?.href
        val userLink: String? = links?.find {
            it.klass?.contains(userLabel) ?: false
        }?.href
    }

    companion object {
        const val userHousesClassLabel: String = "user-houses"
        const val userLabel: String = "user"
    }
}