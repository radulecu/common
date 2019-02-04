package ro.rasel.utils.criptography.key;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ro.rasel.utils.encoding.EncodingUtils;
import ro.rasel.utils.criptography.CypherAlgorithm;

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
public class SecretKeySpecGeneratorTest {
    private final SecretKeyGenerator secretKeyGenerator;
    private final int keySize;

    public SecretKeySpecGeneratorTest(CypherAlgorithm cypherAlgorithm) {
        this.secretKeyGenerator = new SecretKeyGenerator(cypherAlgorithm.getCipherAlgorithm().getAlgorithm());
        this.keySize = cypherAlgorithm.getKeySize();
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.stream(CypherAlgorithm.values()).filter(CypherAlgorithm::isSymmetric)
                .map(a -> new CypherAlgorithm[]{a}).collect(Collectors.toList());
    }

    @Test
    public void assertThatConversionOfKeysToBytesIsReversible() throws NoSuchAlgorithmException {
        Key key = secretKeyGenerator.generateNewKey(keySize);
        byte[] keyBytes = secretKeyGenerator.toBytes(key);

        assertThat(EncodingUtils.toString(secretKeyGenerator.toBytes(secretKeyGenerator.toKey(keyBytes))),
                is(EncodingUtils.toString(keyBytes)));
    }

    @Test
    public void assertThatEveryTimeANewKeyIsGenerated() throws NoSuchAlgorithmException {
        Key key = secretKeyGenerator.generateNewKey(keySize);
        Key key2 = secretKeyGenerator.generateNewKey(keySize);

        assertThat(secretKeyGenerator.toBytes(key), not(secretKeyGenerator.toBytes(key2)));
    }

    @Test
    public void assertNullsReturnNulls() {
        assertNull(secretKeyGenerator.toBytes(null));
        assertNull(secretKeyGenerator.toKey(null));
    }
}