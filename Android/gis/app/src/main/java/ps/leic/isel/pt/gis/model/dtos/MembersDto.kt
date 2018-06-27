package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.siren.Siren
import ps.leic.isel.pt.gis.utils.Requester.Companion.mapper

class MembersDto(siren: Siren) {
    val members = siren.properties?.get("house-members");
    val houseHold: Array<MemberDto> = mapper.convertValue<Array<MemberDto>>(members, Array<MemberDto>::class.java)
    val links: HouseHoldLink = HouseHoldLink(siren.links)

    class HouseHoldLink(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(houseHoldClassLabel) ?: false
        }?.href
        val houseLink: String? = links?.find {
            it.klass?.contains(houseLabel) ?: false
        }?.href
    }

    companion object {
        private const val houseHoldClassLabel: String = "household"
        private const val houseLabel: String = "house"
        private const val addHouseLabel: String = "add-house"
    }
}