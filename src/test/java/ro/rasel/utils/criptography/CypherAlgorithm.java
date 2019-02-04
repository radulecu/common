package ro.rasel.utils.criptography;

/**
 * This is an enum of supported and tested algorithms.
 */
public enum CypherAlgorithm {
    RSA_ECB_PKCS1Padding(CipherAlgorithmImpl.RSA_ECB_PKCS1, false),
    DES_ECB_PKCS5Padding_56(CipherAlgorithmImpl.DES_ECB_PKCS5, 56, true),
    DES_CBC_PKCS5Padding_56(CipherAlgorithmImpl.DES_CBC_PKCS5, 56, 8, true),
    AES_ECB_PKCS5Padding_128(CipherAlgorithmImpl.AES_ECB_PKCS5, 128, true),
    AES_ECB_PKCS5Padding_192(CipherAlgorithmImpl.AES_ECB_PKCS5, 192, true),
    AES_ECB_PKCS5Padding_256(CipherAlgorithmImpl.AES_ECB_PKCS5, 256, true),
    AES_CBC_PKCS5Padding_128(CipherAlgorithmImpl.AES_CBC_PKCS5, 128, 16, true),
    AES_CBC_PKCS5Padding_192(CipherAlgorithmImpl.AES_CBC_PKCS5, 192, 16, true),
    AES_CBC_PKCS5Padding_256(CipherAlgorithmImpl.AES_CBC_PKCS5, 256, 16, true),
    DESede_ECB_PKCS5Padding_112(CipherAlgorithmImpl.DESEDE_ECB_PKCS5, 112, true),
    DESede_ECB_PKCS5Padding_168(CipherAlgorithmImpl.DESEDE_ECB_PKCS5, 168, true),
    DESede_CBC_PKCS5Padding_112(CipherAlgorithmImpl.DESEDE_CBC_PKCS5, 112, 8, true),
    DESede_CBC_PKCS5Padding_168(CipherAlgorithmImpl.DESEDE_CBC_PKCS5, 168, 8, true),
    ;
    private final CipherAlgorithm cipherAlgorithm;
    private final Integer keySize;
    private final Integer ivSize;
    private final boolean symmetric;

    CypherAlgorithm(CipherAlgorithm cipherAlgorithm, boolean symmetric) {
        this(cipherAlgorithm, null, symmetric);
    }

    CypherAlgorithm(CipherAlgorithm cipherAlgorithm, Integer keySize, boolean symmetric) {
        this(cipherAlgorithm, keySize, null, symmetric);
    }

    CypherAlgorithm(CipherAlgorithm cipherAlgorithm, Integer keySize, Integer ivSize, boolean symmetric) {
        this.cipherAlgorithm = cipherAlgorithm;
        this.keySize = keySize;
        this.ivSize = ivSize;
        this.symmetric = symmetric;
    }

    public CipherAlgorithm getCipherAlgorithm() {
        return cipherAlgorithm;
    }

    public Integer getKeySize() {
        return keySize;
    }

    public Integer getIvSize() {
        return ivSize;
    }

    public boolean isSymmetric() {
        return symmetric;
    }
}