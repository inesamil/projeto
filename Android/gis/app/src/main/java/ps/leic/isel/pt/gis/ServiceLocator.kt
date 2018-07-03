package ps.leic.isel.pt.gis

import android.content.Context
import ps.leic.isel.pt.gis.httpRequest.HttpWebService
import ps.leic.isel.pt.gis.httpRequest.HttpWebServiceImpl
import ps.leic.isel.pt.gis.repositories.Repository
import ps.leic.isel.pt.gis.repositories.RepositoryImpl
import ps.leic.isel.pt.gis.stores.CredentialsStore
import ps.leic.isel.pt.gis.stores.CredentialsStoreImpl
import ps.leic.isel.pt.gis.stores.SmartLock
import ps.leic.isel.pt.gis.stores.SmartLockImpl

object ServiceLocator {

    private var httpWebService: HttpWebService? = null
    private var repository: Repository? = null
    private var smartLock: SmartLock? = null
    private var credentialsStore: CredentialsStore? = null

    fun getWebService(context: Context): HttpWebService {
        if (httpWebService == null)
            httpWebService = HttpWebServiceImpl(context)
        return httpWebService as HttpWebService
    }

    fun getRepository(context: Context): Repository {
        if (repository == null) {
            repository = RepositoryImpl(context)
        }
        return repository as Repository
    }

    fun getSmartLock(context: Context): SmartLock {
        if (smartLock == null) {
            smartLock = SmartLockImpl(context)
        }
        return smartLock as SmartLock
    }

    fun getCredentialsStore(context: Context): CredentialsStore {
        if (credentialsStore == null) {
            credentialsStore = CredentialsStoreImpl(context)
        }
        return credentialsStore as CredentialsStore
    }
}