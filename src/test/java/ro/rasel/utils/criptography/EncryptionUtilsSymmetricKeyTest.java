package ro.rasel.utils.criptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
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
    private final CryptographyAlgorithm cryptographyAlgorithm;
    private final EncryptionUtils encryptionUtils;
    private final Key key;

    public EncryptionUtilsSymmetricKeyTest(String text, CryptographyAlgorithm cryptographyAlgorithm) {
        this.text = text;
        this.cryptographyAlgorithm = cryptographyAlgorithm;
        this.encryptionUtils = new EncryptionUtils(cryptographyAlgorithm.getCypherAlgorithm());
        this.key = new SecretKeySpec(EncodingUtils.toBytes("testpasswordgdgsd234124123fgsddf"),cryptographyAlgorithm.getCypherAlgorithm());
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> result = new ArrayList<>();
        Arrays.asList("test", "(teststring)*(^*&787867", null, "", "\u4321\u3395\u2121").stream().forEach(
                s -> Arrays.asList(CryptographyAlgorithm.values()).stream().filter(c -> c.isSimetryc())
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

    //
    @Test
    public void assertNullsReturnNulls()
            throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        assertNull(encryptionUtils.encryptMessage(null, key));
        assertNull(encryptionUtils.decryptMessage(null, key));
    }
}