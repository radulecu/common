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
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return Stream.of("test", "(teststring)*(^*&787867", null, "", "\u4321\u3395\u2121").flatMap(
                t -> Arrays.stream(EncryptionAlgorithm.values()).filter(EncryptionAlgorithm::isSymmetric)
                        .filter(a -> ECB.equals(a.getCipherAlgorithm().getCipherMode()))
                        .map(a -> new Object[]{t, a})).collect(Collectors.toList());
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