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
public class EncryptionUtilsSymmetricKeyTest {
    private final String text;
    private final SymetricCryptographyAlgorithmEnum cryptographyAlgorithm;
    private final EncryptionUtils encryptionUtils;
    private final Key key;

    public EncryptionUtilsSymmetricKeyTest(String text, SymetricCryptographyAlgorithmEnum cryptographyAlgorithm)
            throws NoSuchAlgorithmException {
        this.text = text;
        this.cryptographyAlgorithm = cryptographyAlgorithm;
        this.encryptionUtils = new EncryptionUtils(cryptographyAlgorithm.getCypherAlgorithm());
        this.key = new KeySpecUtils(cryptographyAlgorithm.getAlgorithm())
                .generateNewKeySpec(cryptographyAlgorithm.getKeySize());
    }

    @Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> result = new ArrayList<>();
        Arrays.asList("test", "(teststring)*(^*&787867", null, "", "\u4321\u3395\u2121").stream().forEach(
                s -> Arrays.asList(SymetricCryptographyAlgorithmEnum.values()).stream()
                        .forEach(c -> result.add(new Object[]{s, c}))
        );
        return result;
    }

    @Test
    public void assertThatEncriptionOfBytesIsReversible()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
            NoSuchPaddingException {
        byte[] bytes = EncodingUtils.toBytes(text);

        assertThat(EncodingUtils.toString(encryptionUtils
                        .decryptMessage(encryptionUtils.encryptMessage(bytes, key), key)),
                is(text));

    }

    @Test
    public void assertNullsReturnNulls()
            throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        assertNull(encryptionUtils.encryptMessage(null, key));
        assertNull(encryptionUtils.decryptMessage(null, key));
    }
}