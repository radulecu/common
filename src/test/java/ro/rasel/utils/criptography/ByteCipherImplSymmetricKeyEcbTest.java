package ro.rasel.utils.criptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ro.rasel.criptography.ByteCipherImpl;
import ro.rasel.criptography.CipherAlgorithm;
import ro.rasel.criptography.CipherBlockMode;
import ro.rasel.criptography.CipherMode;
import ro.rasel.criptography.key.SecretKeyGenerator;
import ro.rasel.encoding.EncodingUtils;

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
public class ByteCipherImplSymmetricKeyEcbTest {
    private static final CipherBlockMode ECB = CipherBlockMode.ECB;
    private final String text;
    private final ByteCipherImpl cipherUtils;
    private final Key key;

    public ByteCipherImplSymmetricKeyEcbTest(String text, CypherAlgorithm algorithm) throws NoSuchAlgorithmException {
        this.text = text;
        CipherAlgorithm encryptionAlgorithm = algorithm.getCipherAlgorithm();
        this.cipherUtils = new ByteCipherImpl(encryptionAlgorithm);
        this.key = new SecretKeyGenerator(encryptionAlgorithm.getAlgorithm()).generateNewKey(algorithm.getKeySize());
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Stream.of("test", "(teststring)*(^*&787867", null, "", "\u4321\u3395\u2121").flatMap(
                t -> Arrays.stream(CypherAlgorithm.values()).filter(CypherAlgorithm::isSymmetric)
                        .filter(a -> ECB.toString().equals(a.getCipherAlgorithm().getCipherBlockMode()))
                        .map(a -> new Object[]{t, a})).collect(Collectors.toList());
    }

    @Test
    public void assertThatEncryptionOfBytesIsReversible()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
            NoSuchPaddingException {
        byte[] bytes = EncodingUtils.toBytes(text);
        byte[] encryptedMessage = cipherUtils.convert(bytes, CipherMode.ENCRYPT_MODE, key);
        byte[] decryptedMessage = cipherUtils.convert(encryptedMessage, CipherMode.DECRYPT_MODE, key);
        assertThat(EncodingUtils.toString(decryptedMessage),
                is(text));
    }

    @Test
    public void assertNullMessagesReturnNulls()
            throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        assertNull(cipherUtils.convert((byte[]) null, CipherMode.ENCRYPT_MODE, key));
        assertNull(cipherUtils.convert((byte[]) null, CipherMode.DECRYPT_MODE, key));
    }
}