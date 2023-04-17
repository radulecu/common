package ro.rasel.key;

import org.junit.Test;
import ro.rasel.criptography.key.KeyPairGenerator;
import ro.rasel.criptography.key.KeyPairGeneratorImpl;
import ro.rasel.encoding.EncodingUtils;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class KeyPairGeneratorTest {
    private final KeyPairGenerator rsaEncodingUtils = new KeyPairGeneratorImpl("RSA");

    @Test
    public void assertThatConversionOfKeysToBytesIsReversible()
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyPair keyPair = rsaEncodingUtils.generateNewKeyPair(2048);

        byte[] privateKey = rsaEncodingUtils.toBytes(keyPair.getPrivate());
        byte[] publicKey = rsaEncodingUtils.toBytes(keyPair.getPublic());

        assertThat(EncodingUtils.toString(rsaEncodingUtils.toBytes(rsaEncodingUtils.toPrivateKey(privateKey))),
                is(EncodingUtils.toString(privateKey)));
        assertThat(EncodingUtils.toString(rsaEncodingUtils.toBytes(rsaEncodingUtils.toPublicKey(publicKey))),
                is(EncodingUtils.toString(publicKey)));
    }

    @Test
    public void assertThatEveryTimeANewKeyIsGenerated() throws NoSuchAlgorithmException {
        KeyPair keyPair = rsaEncodingUtils.generateNewKeyPair(2048);
        KeyPair keyPair2 = rsaEncodingUtils.generateNewKeyPair(2048);

        assertThat(rsaEncodingUtils.toBytes(keyPair.getPublic()), not(rsaEncodingUtils.toBytes(keyPair2.getPublic())));
        assertThat(rsaEncodingUtils.toBytes(keyPair.getPrivate()),
                not(rsaEncodingUtils.toBytes(keyPair2.getPrivate())));
    }

    @Test
    public void assertNullsReturnNulls() throws InvalidKeySpecException, NoSuchAlgorithmException {
        assertNull(rsaEncodingUtils.toBytes(null));
        assertNull(rsaEncodingUtils.toPrivateKey(null));
        assertNull(rsaEncodingUtils.toPublicKey(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenKeySizeIsNegativeThenGenerateNewKeypairThrowsIllegalArgumentException()
            throws NoSuchAlgorithmException {
        rsaEncodingUtils.generateNewKeyPair(-1);
    }
}