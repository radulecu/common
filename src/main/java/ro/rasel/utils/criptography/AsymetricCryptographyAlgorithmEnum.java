package ro.rasel.utils.criptography;

public enum AsymetricCryptographyAlgorithmEnum {
    RSA_ECB_PKCS1Padding("RSA", "RSA/ECB/PKCS1Padding"),
//    RSA_ECB_OAEPWithSHA1AndMGF1Padding("RSA", "RSA/ECB/OAEPWithSHA-1AndMGF1Padding"),
//    RSA_ECB_OAEPWithSHA256AndMGF1Padding("RSA", "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"),
    ;

    private final String cypherAlgorithm;
    private final String algorithm;

    AsymetricCryptographyAlgorithmEnum(String algorithm, String cypherAlgorithm) {
        this.algorithm = algorithm;
        this.cypherAlgorithm = cypherAlgorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getCypherAlgorithm() {
        return cypherAlgorithm;
    }
}
