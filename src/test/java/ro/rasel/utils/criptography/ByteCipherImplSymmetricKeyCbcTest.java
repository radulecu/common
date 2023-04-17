package ro.rasel.utils.criptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ro.rasel.criptography.ByteCipherImpl;
import ro.rasel.criptography.CipherAlgorithm;
import ro.rasel.criptography.CipherBlockMode;
import ro.rasel.criptography.CipherMode;
import ro.rasel.criptography.key.IVParameterSpecGenerator;
import ro.rasel.criptography.key.SecretKeyGenerator;
import ro.rasel.encoding.EncodingUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ByteCipherImplSymmetricKeyCbcTest {
    private static final CipherBlockMode CBC = CipherBlockMode.CBC;
    private final String text;
    private final ByteCipherImpl cipherUtils;
    private final Key key;
    private final AlgorithmParameterSpec algorithmParameterSpec;

    public ByteCipherImplSymmetricKeyCbcTest(String text, CypherAlgorithm algorithm) throws NoSuchAlgorithmException {
        this.text = text;
        CipherAlgorithm cipherAlgorithm = algorithm.getCipherAlgorithm();
        this.cipherUtils = new ByteCipherImpl(cipherAlgorithm);
        this.key = new SecretKeyGenerator(cipherAlgorithm.getAlgorithm()).generateNewKey(algorithm.getKeySize());
        algorithmParameterSpec =
                new IVParameterSpecGenerator().generateNewParameterSpec(algorithm.getIvSize());
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Stream.of("test", "(teststring)*(^*&787867", null, "", "\u4321\u3395\u2121").flatMap(
                t -> Arrays.stream(CypherAlgorithm.values()).filter(CypherAlgorithm::isSymmetric)
                        .filter(a -> CBC.toString().equals(a.getCipherAlgorithm().getCipherBlockMode()))
                        .map(a -> new Object[]{t, a})).collect(Collectors.toCollection(ArrayList::new));
    }

    @Test
    public void assertThatEncryptionOfBytesIsReversible()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
            NoSuchPaddingException, InvalidAlgorithmParameterException {
        byte[] bytes = EncodingUtils.toBytes(text);
        byte[] encryptedMessage = cipherUtils.convert(bytes, CipherMode.ENCRYPT_MODE, key, algorithmParameterSpec);
        byte[] decryptedMessage =
                cipherUtils.convert(encryptedMessage, CipherMode.DECRYPT_MODE, key, algorithmParameterSpec);
        assertThat(EncodingUtils.toString(decryptedMessage), is(text));
    }

    @Test
    public void assertNullMessagesReturnNulls()
            throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException {
        assertNull(cipherUtils.convert((byte[]) null, CipherMode.ENCRYPT_MODE, key, algorithmParameterSpec));
        assertNull(cipherUtils.convert((byte[]) null, CipherMode.DECRYPT_MODE, key, algorithmParameterSpec));
    }
}