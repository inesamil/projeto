package ps.leic.isel.pt.gis

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import ps.leic.isel.pt.gis.httpRequest.HttpWebService
import ps.leic.isel.pt.gis.httpRequest.HttpWebServiceImpl
import ps.leic.isel.pt.gis.repositories.Repository
import ps.leic.isel.pt.gis.repositories.RepositoryImpl

object ServiceLocator {

    private var httpWebService: HttpWebService? = null
    private const val CREDENTIALS_TAG = "credentials"
    private var repository: Repository? = null

    fun getWebService(context: Context): HttpWebService {
        if (httpWebService == null)
            httpWebService = HttpWebServiceImpl(context)
        return httpWebService as HttpWebService
    }

    fun getUserCredentials(context: Context): String {
        val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return mPreferences.getString(CREDENTIALS_TAG, "")
    }

    fun getRepository(context: Context): Repository {
        if (repository == null) {
            repository = RepositoryImpl(context)
        }
        return repository as Repository
    }
}