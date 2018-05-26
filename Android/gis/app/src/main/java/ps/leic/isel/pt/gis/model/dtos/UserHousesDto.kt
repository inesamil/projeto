package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class UserHousesDto(siren: Siren) {
    val houses: Array<HouseDto>
    val links: UserHousesLinks

    init {
        houses = siren.entities?.map {
            HouseDto(Siren(it.klass, it.properties, null, it.actions, it.links))
        }.orEmpty().toTypedArray()
        links = UserHousesLinks(siren.links)
    }

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