package ps.leic.isel.pt.gis.model.dtos

import ps.leic.isel.pt.gis.hypermedia.siren.Siren
import ps.leic.isel.pt.gis.hypermedia.siren.subentities.Action

class InvitationDto(siren: Siren) {
    val houseId: Long
    val houseName: String
    val username: String
    val actions: InvitationActions

    init {
        val properties = siren.properties
        houseId = (properties?.get(houseIdLabel) as Int).toLong()
        houseName = properties[houseNameLabel] as String
        username = properties[usernameLabel] as String
        actions = InvitationActions(siren.actions)
    }

    class InvitationActions(actions: Array<Action>?) {
        var updateInvitation: ActionDto? = null

        init {
            actions?.find { it.name == updateInvitationLabel }?.let {
                updateInvitation = ActionDto(it.name, it.href, it.type)
            }
        }
    }

    companion object {
        private const val houseIdLabel: String = "house-id"
        private const val houseNameLabel: String = "house-name"
        private const val usernameLabel: String = "user-username"
        private const val updateInvitationLabel: String = "update-invitation"
    }
}