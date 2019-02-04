package ro.rasel.utils.criptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ro.rasel.utils.criptography.key.KeyPairGenerator;
import ro.rasel.utils.criptography.key.KeyPairGeneratorImpl;
import ro.rasel.utils.encoding.EncodingUtils;

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
import static ro.rasel.utils.criptography.CypherAlgorithm.RSA_ECB_PKCS1Padding;

@RunWith(Parameterized.class)
public class ByteCipherImplAsymmetricKeyTest {
    private final String text;
    private final KeyPairGenerator keyPairGenerator;
    private final ByteCipherImpl cipherUtils;

    public ByteCipherImplAsymmetricKeyTest(String text) {
        this.text = text;
        CipherAlgorithm encryptionAlgorithm = RSA_ECB_PKCS1Padding.getCipherAlgorithm();
        keyPairGenerator = new KeyPairGeneratorImpl(encryptionAlgorithm.getAlgorithm());
        cipherUtils = new ByteCipherImpl(encryptionAlgorithm);
    }

    @Parameters
    public static Collection<String[]> data() {
        return Arrays.asList(new String[][]{{"test"}, {"(teststring)*(^*&787867"}, {null}, {""}});
    }

    @Test
    public void assertThatEncryptionOfBytesIsReversible()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
            NoSuchPaddingException {
        KeyPair keyPair = keyPairGenerator.generateNewKeyPair(2048);
        byte[] bytes = EncodingUtils.toBytes(text);

        assertThat(EncodingUtils.toString(cipherUtils.convert(
                cipherUtils.convert(bytes, CipherMode.ENCRYPT_MODE, keyPair.getPrivate()),
                CipherMode.DECRYPT_MODE, keyPair.getPublic())), is(text));
    }

    @Test
    public void assertNullMessagesReturnNulls()
            throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        KeyPair keyPair = keyPairGenerator.generateNewKeyPair(2048);
        assertNull(cipherUtils.convert((byte[]) null, CipherMode.ENCRYPT_MODE, keyPair.getPrivate()));
        assertNull(cipherUtils.convert((byte[]) null, CipherMode.DECRYPT_MODE, keyPair.getPublic()));
    }
}