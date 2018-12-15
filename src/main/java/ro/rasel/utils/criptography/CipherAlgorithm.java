package ro.rasel.utils.criptography;

import java.util.Objects;

import static ro.rasel.utils.criptography.CipherMode.CBC;
import static ro.rasel.utils.criptography.CipherMode.ECB;
import static ro.rasel.utils.criptography.CipherPadding.PKCS1Padding;
import static ro.rasel.utils.criptography.CipherPadding.PKCS5Padding;

public class CipherAlgorithm implements ICipherAlgorithm {
    public static final CipherAlgorithm RSA_ECB_PKCS1 = new CipherAlgorithm("RSA", ECB, PKCS1Padding);
    public static final CipherAlgorithm DES_ECB_PKCS5 = new CipherAlgorithm("DES", ECB, PKCS5Padding);
    public static final CipherAlgorithm DES_CBC_PKCS5 = new CipherAlgorithm("DES", CBC, PKCS5Padding);
    public static final CipherAlgorithm AES_ECB_PKCS5 = new CipherAlgorithm("AES", ECB, PKCS5Padding);
    public static final CipherAlgorithm AES_CBC_PKCS5 = new CipherAlgorithm("AES", CBC, PKCS5Padding);
    public static final CipherAlgorithm DESEDE_ECB_PKCS5 = new CipherAlgorithm("DESede", ECB, PKCS5Padding);
    public static final CipherAlgorithm DESEDE_CBC_PKCS5 = new CipherAlgorithm("DESede", CBC, PKCS5Padding);

    private final String algorithm;
    private final String cipherMode;
    private final String padding;

    public CipherAlgorithm(String algorithm, CipherMode cipherMode, CipherPadding padding) {
        this(algorithm, cipherMode.toString(), padding.toString());
    }

    public CipherAlgorithm(String algorithm, String cipherMode, String padding) {
        this.algorithm = algorithm;
        this.cipherMode = cipherMode;
        this.padding = padding;
    }

    @Override
    public String getAlgorithm() {
        return algorithm;
    }

    @Override
    public String getCipherMode() {
        return cipherMode;
    }

    @Override
    public String getPadding() {
        return padding;
    }

    @Override
    public String getCipherAlgorithm() {
        return getAlgorithm() + "/" + getCipherMode() + "/" + getPadding();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CipherAlgorithm that = (CipherAlgorithm) o;
        return Objects.equals(algorithm, that.algorithm) && cipherMode == that.cipherMode && padding == that.padding;
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm, cipherMode, padding);
    }

    @Override
    public String toString() {
        return "EncryptionAlgorithm{" +
                "algorithm='" + algorithm + '\'' +
                ", cipherMode=" + cipherMode +
                ", padding=" + padding +
                '}';
    }
}