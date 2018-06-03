package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Siren

class MemberDto(siren: Siren) {
    val houseId: Long
    val username: String
    val administrator: Boolean
    val links: MemberLynks

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        username = properties[usernameLabel] as String
        administrator = properties[adminstratorLabel] as Boolean
        links = MemberLynks(siren.links)
    }

    class MemberLynks(links: Array<Link>?) {
        val userLink: String? = links?.find {
            it.klass?.contains(userLabel) ?: false
        }?.href
    }

    companion object {
        private const val houseIdLabel: String = "house-id"
        private const val usernameLabel: String = "user-username"
        private const val adminstratorLabel: String = "household-administrator"
        private const val userLabel: String = "user"
    }
}
