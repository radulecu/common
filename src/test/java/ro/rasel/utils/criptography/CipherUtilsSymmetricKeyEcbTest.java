package ro.rasel.utils.criptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class CipherUtilsSymmetricKeyEcbTest {
    private static final CipherMode ECB = CipherMode.ECB;
    private final String text;
    private final CipherUtils cipherUtils;
    private final Key key;

    public CipherUtilsSymmetricKeyEcbTest(String text, EncryptionAlgorithm algorithm) throws NoSuchAlgorithmException {
        this.text = text;
        ICipherAlgorithm encryptionAlgorithm = algorithm.getCipherAlgorithm();
        this.cipherUtils = new CipherUtils(encryptionAlgorithm);
        this.key = new KeySpecUtils(encryptionAlgorithm.getAlgorithm()).generateNewKeySpec(algorithm.getKeySize());
    }

    @Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> result = new ArrayList<>();
        Arrays.asList("test", "(teststring)*(^*&787867", null, "", "\u4321\u3395\u2121").stream().forEach(
                t -> Arrays.asList(EncryptionAlgorithm.values()).stream().filter(EncryptionAlgorithm::isSymmetric)
                        .filter(a -> ECB.equals(a.getCipherAlgorithm().getCipherMode()))
                        .forEach(a -> result.add(new Object[]{t, a})));
        return result;
    }

    @Test
    public void assertThatEncriptionOfBytesIsReversible()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
            NoSuchPaddingException {
        byte[] bytes = EncodingUtils.toBytes(text);
        assertThat(EncodingUtils.toString(cipherUtils.decryptMessage(cipherUtils.encryptMessage(bytes, key), key)),
                is(text));
    }

    @Test
    public void assertNullsReturnNulls()
            throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        assertNull(cipherUtils.encryptMessage(null, key));
        assertNull(cipherUtils.decryptMessage(null, key));
    }
}