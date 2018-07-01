package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.body.InvitationBody1
import ps.leic.isel.pt.gis.model.body.InvitationBody2
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
import ps.leic.isel.pt.gis.model.dtos.HouseDto
import ps.leic.isel.pt.gis.model.dtos.InvitationDto
import ps.leic.isel.pt.gis.model.dtos.InvitationsDto
import ps.leic.isel.pt.gis.repositories.Resource

class InvitationsViewModel(private val app: Application) : AndroidViewModel(app) {

    private lateinit var url: String
    private var invitations: LiveData<Resource<InvitationsDto, ErrorDto>>? = null

    fun init(url: String) {
        if (invitations != null && this.url == url) return
        if (invitations != null) cancel()
        this.url = url
        invitations = ServiceLocator.getRepository(app.applicationContext)
                .get(InvitationsDto::class.java, ErrorDto::class.java, url, TAG)
    }

    fun getInvitations(): LiveData<Resource<InvitationsDto, ErrorDto>>? {
        return invitations
    }

    fun sendInvitation(from_house: HouseDto, to_username: String) : LiveData<Resource<Any, ErrorDto>>? {
        from_house.actions.sendInvitation?.let {
            val invitationBody = InvitationBody1(to_username)
            return ServiceLocator.getRepository(app.applicationContext)
                    .create(Any::class.java, ErrorDto::class.java, it.url, it.contentType, invitationBody, TAG)
        }
        return null
    }

    fun acceptInvitation(invitation: InvitationDto) : LiveData<Resource<Any, ErrorDto>>? {
        invitation.actions.updateInvitation?.let {
            val invitationBody = InvitationBody2(true)
            return ServiceLocator.getRepository(app.applicationContext)
                    .update(Any::class.java, ErrorDto::class.java, it.url, it.contentType, invitationBody, TAG)

        }
        return null
    }

    fun declineInvitation(invitation: InvitationDto) : LiveData<Resource<Any, ErrorDto>>? {
        invitation.actions.updateInvitation?.let {
            val invitationBody = InvitationBody2(false)
            return ServiceLocator.getRepository(app.applicationContext)
                    .update(Any::class.java, ErrorDto::class.java, it.url, it.contentType, invitationBody, TAG)

        }
        return null
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        invitations = null
    }

    companion object {
        const val TAG: String = "InvitationsViewModel"
    }
}