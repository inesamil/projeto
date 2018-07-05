package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.Siren
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Link

class InvitationsDto(siren: Siren) {
    val invitations: Array<InvitationDto> = siren.entities?.map {
        InvitationDto(Siren(it.klass, it.properties, null, it.actions, it.links))
    }.orEmpty().toTypedArray()
    val actions: InvitationsActions = InvitationsActions(siren.actions)
    val links: InvitationsLinks = InvitationsLinks(siren.links)

    class InvitationsActions(actions: Array<Action>?) {
        var addInvitation: ActionDto? = null

        init {
            actions?.find { it.name == addInvitationLabel }?.let {
                addInvitation = ActionDto(it.name, it.href, it.type)
            }
        }
    }

    class InvitationsLinks(links: Array<Link>?) {
        val housesLink: String? = links?.find {
            it.klass?.contains(housesLabel) ?: false
        }?.href
    }

    companion object {
        private const val addInvitationLabel: String = "add-invitation"
        private const val housesLabel: String = "houses"
    }
}