package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class MemberDto(siren: Siren) {
    val houseId: Long
    val username: String
    val administrator: Boolean
    val links: MemberLynks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        username = properties?.get(usernameLabel) as String
        administrator = properties?.get(adminstratorLabel) as Boolean
        links = MemberLynks(siren.links)
    }

    class MemberLynks(links: Array<Link>?) {
        val userLink: String? = links?.find {
            it.klass?.contains(userLabel) ?: false
        }?.href
    }

    companion object {
        const val houseIdLabel: String = "house-id"
        const val usernameLabel: String = "user-username"
        const val adminstratorLabel: String = "household-administrator"
        const val userLabel: String = "user"
    }
}
