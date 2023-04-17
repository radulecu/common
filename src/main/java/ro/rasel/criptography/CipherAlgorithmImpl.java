package ro.rasel.criptography;

import java.util.Objects;

public class CipherAlgorithmImpl implements CipherAlgorithm {
    public static final CipherAlgorithmImpl RSA_ECB_PKCS1 = new CipherAlgorithmImpl("RSA", CipherBlockMode.ECB, CipherPadding.PKCS1Padding);
    public static final CipherAlgorithmImpl DES_ECB_PKCS5 = new CipherAlgorithmImpl("DES", CipherBlockMode.ECB, CipherPadding.PKCS5Padding);
    public static final CipherAlgorithmImpl DES_CBC_PKCS5 = new CipherAlgorithmImpl("DES", CipherBlockMode.CBC, CipherPadding.PKCS5Padding);
    public static final CipherAlgorithmImpl AES_ECB_PKCS5 = new CipherAlgorithmImpl("AES", CipherBlockMode.ECB, CipherPadding.PKCS5Padding);
    public static final CipherAlgorithmImpl AES_CBC_PKCS5 = new CipherAlgorithmImpl("AES", CipherBlockMode.CBC, CipherPadding.PKCS5Padding);
    public static final CipherAlgorithmImpl DESEDE_ECB_PKCS5 = new CipherAlgorithmImpl("DESede", CipherBlockMode.ECB, CipherPadding.PKCS5Padding);
    public static final CipherAlgorithmImpl DESEDE_CBC_PKCS5 = new CipherAlgorithmImpl("DESede", CipherBlockMode.CBC, CipherPadding.PKCS5Padding);

    private final String algorithm;
    private final String cipherBlockMode;
    private final String padding;

    public CipherAlgorithmImpl(String algorithm, CipherBlockMode cipherBlockMode, CipherPadding padding) {
        this(algorithm, cipherBlockMode.toString(), padding.toString());
    }

    public CipherAlgorithmImpl(String algorithm, String cipherBlockMode, String padding) {
        this.algorithm = algorithm;
        this.cipherBlockMode = cipherBlockMode;
        this.padding = padding;
    }

    @Override
    public String getAlgorithm() {
        return algorithm;
    }

    @Override
    public String getCipherBlockMode() {
        return cipherBlockMode;
    }

    @Override
    public String getPadding() {
        return padding;
    }

    @Override
    public String getCipherAlgorithm() {
        return getAlgorithm() + "/" + getCipherBlockMode() + "/" + getPadding();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CipherAlgorithmImpl that = (CipherAlgorithmImpl) o;
        return Objects.equals(algorithm, that.algorithm) &&
                Objects.equals(cipherBlockMode, that.cipherBlockMode) &&
                Objects.equals(padding, that.padding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm, cipherBlockMode, padding);
    }

    @Override
    public String toString() {
        return "CipherAlgorithmImpl{" +
                "algorithm='" + algorithm + '\'' +
                ", cipherBlockMode='" + cipherBlockMode + '\'' +
                ", padding='" + padding + '\'' +
                '}';
    }
}