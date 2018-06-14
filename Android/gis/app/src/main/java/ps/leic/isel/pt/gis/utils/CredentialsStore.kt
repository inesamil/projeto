package ps.leic.isel.pt.gis.utils

interface CredentialsStore {

    fun getUsername() : String?

    fun getCredentials() : Credentials?

    fun storeCredentials(credentials: Credentials) : Boolean

    fun deleteCredentials() : Boolean

    data class Credentials (val username: String, val password: String)

    companion object {
        const val USERNAME: String = "USERNAME"
        const val PASSWORD: String = "PASSWORD"
    }
}