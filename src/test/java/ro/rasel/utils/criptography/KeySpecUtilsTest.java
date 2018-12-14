package ro.rasel.utils.criptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class KeySpecUtilsTest {
    private final KeySpecUtils keySpecUtils;
    private final int keySize;

    public KeySpecUtilsTest(EncryptionAlgorithm encryptionAlgorithm) {
        this.keySpecUtils = new KeySpecUtils(encryptionAlgorithm.getCipherAlgorithm().getAlgorithm());
        this.keySize = encryptionAlgorithm.getKeySize();
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.stream(EncryptionAlgorithm.values()).filter(EncryptionAlgorithm::isSymmetric)
                .map(a -> new EncryptionAlgorithm[]{a}).collect(Collectors.toList());
    }

    @Test
    public void assertThatConversionOfKeysToBytesIsReversible() throws NoSuchAlgorithmException {
        Key key = keySpecUtils.generateNewKeySpec(keySize);
        byte[] keyBytes = keySpecUtils.toBytes(key);

        assertThat(EncodingUtils.toString(keySpecUtils.toBytes(keySpecUtils.toKey(keyBytes))),
                is(EncodingUtils.toString(keyBytes)));
    }

    @Test
    public void assertThatEverytimeANewKeyIsGenerated() throws NoSuchAlgorithmException {
        Key key = keySpecUtils.generateNewKeySpec(keySize);
        Key key2 = keySpecUtils.generateNewKeySpec(keySize);

        assertThat(keySpecUtils.toBytes(key), not(keySpecUtils.toBytes(key2)));
    }

    @Test
    public void assertNullsReturnNulls() {
        assertNull(keySpecUtils.toBytes(null));
        assertNull(keySpecUtils.toKey(null));
    }
}