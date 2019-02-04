package ro.rasel.utils.criptography;

import java.util.Objects;

import static ro.rasel.utils.criptography.CipherBlockMode.CBC;
import static ro.rasel.utils.criptography.CipherBlockMode.ECB;
import static ro.rasel.utils.criptography.CipherPadding.PKCS1Padding;
import static ro.rasel.utils.criptography.CipherPadding.PKCS5Padding;

public class CipherAlgorithmImpl implements CipherAlgorithm {
    public static final CipherAlgorithmImpl RSA_ECB_PKCS1 = new CipherAlgorithmImpl("RSA", ECB, PKCS1Padding);
    public static final CipherAlgorithmImpl DES_ECB_PKCS5 = new CipherAlgorithmImpl("DES", ECB, PKCS5Padding);
    public static final CipherAlgorithmImpl DES_CBC_PKCS5 = new CipherAlgorithmImpl("DES", CBC, PKCS5Padding);
    public static final CipherAlgorithmImpl AES_ECB_PKCS5 = new CipherAlgorithmImpl("AES", ECB, PKCS5Padding);
    public static final CipherAlgorithmImpl AES_CBC_PKCS5 = new CipherAlgorithmImpl("AES", CBC, PKCS5Padding);
    public static final CipherAlgorithmImpl DESEDE_ECB_PKCS5 = new CipherAlgorithmImpl("DESede", ECB, PKCS5Padding);
    public static final CipherAlgorithmImpl DESEDE_CBC_PKCS5 = new CipherAlgorithmImpl("DESede", CBC, PKCS5Padding);

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
        return Objects.equals(algorithm, that.algorithm) && cipherBlockMode == that.cipherBlockMode && padding == that.padding;
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