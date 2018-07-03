package ps.leic.isel.pt.gis.security

/*
class SecurityImpl(val applicationContext: Context) : Security {

    override fun generateSecretKey() {
        val keyStore = KeyStore.getInstance(AndroidKeyStore)
        keyStore.load(null)

        if (!keyStore.containsAlias(KEY_ALIAS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, AndroidKeyStore)
                keyGenerator.init(
                        KeyGenParameterSpec.Builder(KEY_ALIAS,
                                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                                .setBlockModes(KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                                .setRandomizedEncryptionRequired(false)
                                .build())
                keyGenerator.generateKey()
            } else {
                // Generate the RSA key pairs
                // Generate a key pair for encryption
                val start = Calendar.getInstance()
                val end = Calendar.getInstance()
                end.add(Calendar.YEAR, 30)
                val spec = KeyPairGeneratorSpec.Builder(applicationContext)
                        .setAlias(KEY_ALIAS)
                        .setSubject(X500Principal("CN=$KEY_ALIAS"))
                        .setSerialNumber(BigInteger.TEN)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build()
                val kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, AndroidKeyStore)
                kpg.initialize(spec)
                kpg.generateKeyPair()

            }
        }
    }

    override fun getSecretKey(): Key {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val keyStore = KeyStore.getInstance(AndroidKeyStore)
            return keyStore.getKey(XEALTH_KEY_ALIAS, null)
        } else {

        }
    }

    override fun encrypt(data: Array<Byte>): Array<Byte> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val c = Cipher.getInstance(AES_MODE)
            c.init(Cipher.ENCRYPT_MODE, getSecretKey(context), GCMParameterSpec(128, FIXED_IV.getBytes()))
            val encodedBytes = c.doFinal(input)
            return Base64.encodeToString(encodedBytes, Base64.DEFAULT)
        } else {

        }
    }

    override fun decrypt(encryptedData: Array<Byte>): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val c = Cipher.getInstance(AES_MODE)
            c.init(Cipher.DECRYPT_MODE, getSecretKey(context), GCMParameterSpec(128, FIXED_IV.getBytes()))
            return c.doFinal(encrypted)
        } else {

        }
    }

    companion object {
        private const val AndroidKeyStore : String = "AndroidKeyStore"
        private const val AES_MODE : String = "AES/GCM/NoPadding"
    }
}*/