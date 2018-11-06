package ro.rasel.utils.criptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class EncryptionUtilsAsymmetricKeyTest {
    public static final AsymetricCryptographyAlgorithmEnum CRYPTOGRAPHY_ALGORITHM = AsymetricCryptographyAlgorithmEnum.RSA_ECB_PKCS1Padding;
    private final String text;
    private final KeyPairUtils keyPairUtils = new KeyPairUtils(
            CRYPTOGRAPHY_ALGORITHM.getAlgorithm());
    private final EncryptionUtils encryptionUtils =
            new EncryptionUtils(AsymetricCryptographyAlgorithmEnum.RSA_ECB_PKCS1Padding.getCypherAlgorithm());

    public EncryptionUtilsAsymmetricKeyTest(String text) {
        this.text = text;
    }

    @Parameters
    public static Collection<String[]> data() {
        return Arrays.asList(new String[][]{{"test"}, {"(teststring)*(^*&787867"}, {null}, {""}});
    }

    @Test
    public void assertThatEncriptionOfBytesIsReversible()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
            NoSuchPaddingException {
        KeyPair keyPair = keyPairUtils.generateNewKeyPair(2048);
        byte[] bytes = EncodingUtils.toBytes(text);

        assertThat(EncodingUtils.toString(encryptionUtils
                        .decryptMessage(encryptionUtils.encryptMessage(bytes, keyPair.getPrivate()), keyPair.getPublic())),
                is(text));

    }

    @Test
    public void assertNullsReturnNulls()
            throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        KeyPair keyPair = keyPairUtils.generateNewKeyPair(2048);
        assertNull(encryptionUtils.encryptMessage(null, keyPair.getPrivate()));
        assertNull(encryptionUtils.decryptMessage(null, keyPair.getPublic()));
    }
}