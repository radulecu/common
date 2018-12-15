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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static ro.rasel.utils.criptography.EncryptionAlgorithm.RSA_ECB_PKCS1Padding;

@RunWith(Parameterized.class)
public class CipherUtilsAsymmetricKeyTest {
    private final String text;
    private final KeyPairUtils keyPairUtils;
    private final CipherUtils cipherUtils;

    public CipherUtilsAsymmetricKeyTest(String text) {
        this.text = text;
        ICipherAlgorithm encryptionAlgorithm = RSA_ECB_PKCS1Padding.getCipherAlgorithm();
        keyPairUtils = new KeyPairUtils(encryptionAlgorithm.getAlgorithm());
        cipherUtils = new CipherUtils(encryptionAlgorithm);
    }

    @Parameters
    public static Collection<String[]> data() {
        return Arrays.asList(new String[][]{{"test"}, {"(teststring)*(^*&787867"}, {null}, {""}});
    }

    @Test
    public void assertThatEncryptionOfBytesIsReversible()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
            NoSuchPaddingException {
        KeyPair keyPair = keyPairUtils.generateNewKeyPair(2048);
        byte[] bytes = EncodingUtils.toBytes(text);

        assertThat(EncodingUtils.toString(cipherUtils
                        .decryptMessage(cipherUtils.encryptMessage(bytes, keyPair.getPrivate()), keyPair.getPublic())),
                is(text));
    }

    @Test
    public void assertNullsReturnNulls()
            throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        KeyPair keyPair = keyPairUtils.generateNewKeyPair(2048);
        assertNull(cipherUtils.encryptMessage(null, keyPair.getPrivate()));
        assertNull(cipherUtils.decryptMessage(null, keyPair.getPublic()));
    }
}