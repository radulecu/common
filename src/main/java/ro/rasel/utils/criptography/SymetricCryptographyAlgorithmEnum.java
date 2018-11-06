package ro.rasel.utils.criptography;

public enum SymetricCryptographyAlgorithmEnum {
    DES_ECB_PKCS5PADDING_256("DES","DES/ECB/PKCS5Padding", 56),
    AES_ECB_PKCS5PADDING_129("AES","AES/ECB/PKCS5Padding", 128),
    AES_ECB_PKCS5PADDING_192("AES","AES/ECB/PKCS5Padding", 192),
    AES_ECB_PKCS5PADDING_256("AES","AES/ECB/PKCS5Padding", 256),
    DESEDE_ECB_PKCS5PADDING_112("DESede","DESede/ECB/PKCS5Padding", 112),
    DESEDE_ECB_PKCS5PADDING_168("DESede","DESede/ECB/PKCS5Padding", 168),
    ;

    private final String algorithm;
    private final String cypherAlgorithm;
    private final int keySize;

    SymetricCryptographyAlgorithmEnum(String algorithm, String cypherAlgorithm, int keySize) {
        this.algorithm = algorithm;
        this.cypherAlgorithm = cypherAlgorithm;
        this.keySize = keySize;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getCypherAlgorithm() {
        return cypherAlgorithm;
    }

    public int getKeySize() {
        return keySize;
    }

}
