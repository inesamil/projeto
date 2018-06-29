package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.ErrorDto
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

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        invitations = null
    }

    companion object {
        const val TAG: String = "InvitationsViewModel"
    }
}