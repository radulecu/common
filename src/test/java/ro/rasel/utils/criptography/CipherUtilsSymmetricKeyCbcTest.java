package ro.rasel.utils.criptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

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
public class CipherUtilsSymmetricKeyCbcTest {
    private static final CipherBlockMode CBC = CipherBlockMode.CBC;
    private final String text;
    private final CipherUtils cipherUtils;
    private final Key key;
    private final AlgorithmParameterSpec algorithmParameterSpec;

    public CipherUtilsSymmetricKeyCbcTest(String text, EncryptionAlgorithm algorithm) throws NoSuchAlgorithmException {
        this.text = text;
        ICipherAlgorithm encryptionAlgorithm = algorithm.getCipherAlgorithm();
        this.cipherUtils = new CipherUtils(encryptionAlgorithm);
        this.key = new KeySpecUtils(encryptionAlgorithm.getAlgorithm()).generateNewKeySpec(algorithm.getKeySize());
        algorithmParameterSpec =
                new KeySpecUtils(encryptionAlgorithm.getAlgorithm()).generateNewIvSpec(algorithm.getIvSize());
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Stream.of("test", "(teststring)*(^*&787867", null, "", "\u4321\u3395\u2121").flatMap(
                t -> Arrays.stream(EncryptionAlgorithm.values()).filter(EncryptionAlgorithm::isSymmetric)
                        .filter(a -> CBC.toString().equals(a.getCipherAlgorithm().getCipherMode()))
                        .map(a -> new Object[]{t, a})).collect(Collectors.toCollection(ArrayList::new));
    }

    @Test
    public void assertThatEncryptionOfBytesIsReversible()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
            NoSuchPaddingException, InvalidAlgorithmParameterException {
        byte[] bytes = EncodingUtils.toBytes(text);
        assertThat(EncodingUtils.toString(cipherUtils.convert(
                cipherUtils.convert(bytes, CipherMode.ENCRYPT_MODE, key, algorithmParameterSpec),
                CipherMode.DECRYPT_MODE, key, algorithmParameterSpec)), is(text));
    }

    @Test
    public void assertNullMessagesReturnNulls()
            throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException {
        assertNull(cipherUtils.convert((byte[]) null, CipherMode.ENCRYPT_MODE, key, algorithmParameterSpec));
        assertNull(cipherUtils.convert((byte[]) null, CipherMode.DECRYPT_MODE, key, algorithmParameterSpec));
    }
}