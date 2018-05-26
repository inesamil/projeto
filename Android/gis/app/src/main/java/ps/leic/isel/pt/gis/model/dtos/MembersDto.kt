package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.subentities.Link
import ps.leic.isel.pt.gis.hypermedia.subentities.Siren

class MembersDto(siren: Siren) {
    val houseHold: Array<MemberDto>
    val links: HouseHoldLink

    init {
        houseHold = siren.entities?.map {
            MemberDto(Siren(it.klass, it.properties, null, it.actions, it.links))
        }.orEmpty().toTypedArray()
        links = HouseHoldLink(siren.links)
    }

    class HouseHoldLink(links: Array<Link>?) {
        val selfLink: String? = links?.find {
            it.klass?.contains(houseHoldClassLabel) ?: false
        }?.href
        val houseLink: String? = links?.find {
            it.klass?.contains(houseLabel) ?: false
        }?.href
    }

    companion object {
        const val houseHoldClassLabel: String = "household"
        const val houseLabel: String = "house"
        const val addHouseLabel: String = "add-house"
    }
}