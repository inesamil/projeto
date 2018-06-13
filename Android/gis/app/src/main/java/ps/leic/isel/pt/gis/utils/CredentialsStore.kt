package ps.leic.isel.pt.gis.utils

interface CredentialsStore {

    fun getUsername() : String?

    fun storeUsername(username: String)

    fun deleteUsername()

    companion object {
        const val USERNAME: String = "USERNAME"
    }
}