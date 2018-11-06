package ro.rasel.utils.criptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class KeySpecUtilsTest {
    private final KeySpecUtils keySpecUtils;
    private final int keySize;

    public KeySpecUtilsTest(SymetricCryptographyAlgorithmEnum symetricCryptographyAlgorithmEnum) {
        this.keySpecUtils = new KeySpecUtils(symetricCryptographyAlgorithmEnum.getAlgorithm());
        this.keySize = symetricCryptographyAlgorithmEnum.getKeySize();
    }

    @Parameters
    public static Collection<SymetricCryptographyAlgorithmEnum> data() {
        return Arrays.asList(SymetricCryptographyAlgorithmEnum.values()).stream().collect(Collectors.toList());
    }

    @Test
    public void assertThatConversionOfKeysToBytesIsReversible() throws NoSuchAlgorithmException {
        Key key = keySpecUtils.generateNewKeySpec(keySize);

        byte[] keyBytes = keySpecUtils.toBytes(key);

        assertThat(EncodingUtils.toString(keySpecUtils.toBytes(
                keySpecUtils.toKey(keyBytes))), is(EncodingUtils.toString(keyBytes)));

    }

    @Test
    public void assertThatEverytimeANewKeyIsGenerated()
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Key key = keySpecUtils.generateNewKeySpec(keySize);
        Key key2 = keySpecUtils.generateNewKeySpec(keySize);

        assertThat(keySpecUtils.toBytes(key), not(keySpecUtils.toBytes(key2)));
    }

    @Test
    public void assertNullsReturnNulls() throws InvalidKeySpecException, NoSuchAlgorithmException {
        assertNull(keySpecUtils.toBytes(null));
        assertNull(keySpecUtils.toKey(null));
    }
}