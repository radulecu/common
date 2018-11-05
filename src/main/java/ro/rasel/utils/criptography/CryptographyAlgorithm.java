package ro.rasel.utils.criptography;

public enum CryptographyAlgorithm {
    AES_CBC_NoPadding("AES"),
//    AES_CBC_NoPadding("AES/CBC/NoPadding"),
//    AES_CBC_PKCS5Padding("AES/CBC/PKCS5Padding"),
//    AES_ECB_NoPadding("AES/ECB/NoPadding"),
//    AES_ECB_PKCS5Padding("AES/ECB/PKCS5Padding"),
//    DES_CBC_NoPadding("DES/CBC/NoPadding"),
//    DES_CBC_PKCS5Padding("DES/CBC/PKCS5Padding"),
//    DES_ECB_NoPadding("DES/ECB/NoPadding"),
//    DES_ECB_PKCS5Padding("DES/ECB/PKCS5Padding"),
//    DESede_CBC_NoPadding("DESede/CBC/NoPadding"),
//    DESede_CBC_PKCS5Padding("DESede/CBC/PKCS5Padding"),
//    DESede_ECB_NoPadding("DESede/ECB/NoPadding"),
//    DESede_ECB_PKCS5Padding("DESede/ECB/PKCS5Padding"),
    RSA_ECB_PKCS1Padding("RSA", "RSA/ECB/PKCS1Padding"),
//    RSA_ECB_OAEPWithSHA1AndMGF1Padding("RSA", "RSA/ECB/OAEPWithSHA-1AndMGF1Padding"),
//    RSA_ECB_OAEPWithSHA256AndMGF1Padding("RSA", "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"),
    ;

    private final String cypherAlgorithm;
    private final String algorithm;

    CryptographyAlgorithm(String cypherAlgorithm) {
        algorithm = null;
        this.cypherAlgorithm = cypherAlgorithm;
    }

    CryptographyAlgorithm(String algorithm, String cypherAlgorithm) {
        this.algorithm = algorithm;
        this.cypherAlgorithm = cypherAlgorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getCypherAlgorithm() {
        return cypherAlgorithm;
    }

    public boolean isSimetryc() {
        return algorithm == null;
    }
}
