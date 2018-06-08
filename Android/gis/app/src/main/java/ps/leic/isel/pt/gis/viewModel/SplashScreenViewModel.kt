package ps.leic.isel.pt.gis.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ps.leic.isel.pt.gis.ServiceLocator
import ps.leic.isel.pt.gis.model.dtos.IndexDto
import ps.leic.isel.pt.gis.repositories.Resource

class SplashScreenViewModel(private val app: Application) : AndroidViewModel(app) {

    private var index: LiveData<Resource<IndexDto>>? = null

    fun init() {
        if (index != null) return
        index = ServiceLocator.getRepository(app.applicationContext).get(IndexDto::class.java, url, TAG)
    }

    fun getIndex(): LiveData<Resource<IndexDto>>? {
        return index
    }

    fun cancel() {
        ServiceLocator.getRepository(app.applicationContext).cancelAllPendingRequests(TAG)
        index = null
    }

    companion object {
        //private const val url: String = "http://192.168.1.19:8081/v1" // NUNO
         private val url: String = "http://192.168.1.76:8081/v1" //INES
        // private val url: String = "http://10.10.4.209:8081/v1" //ISEL
        // private val url: String = "http://192.168.1.10:8081/v1" //CASA //TODO: mudar isto para cada pc :/
        const val TAG: String = "SplashScreenViewModel"
    }
}