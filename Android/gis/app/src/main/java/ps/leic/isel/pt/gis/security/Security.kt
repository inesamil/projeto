package ps.leic.isel.pt.gis.security

import java.security.Key

interface Security {

    fun generateSecretKey()

    fun getSecretKey() : Key

    fun encrypt(data: Array<Byte>) : Array<Byte>

    fun decrypt(encryptedData: Array<Byte>) : String
}