package ro.rasel.utils.criptography;

import org.junit.Test;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class KeyPairEncryptionUtilsTest {
    private final KeyPairEncryptionUtils rsaEncodingUtils = new KeyPairEncryptionUtils("RSA");

    @Test
    public void assertThatConversionOfKeysToBase64IsReversible()
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyPair keyPair = rsaEncodingUtils.generateNewKeypair(2048);

        byte[] privateKey = rsaEncodingUtils.convertKeyToBase64(keyPair.getPrivate());
        byte[] publicKey = rsaEncodingUtils.convertKeyToBase64(keyPair.getPublic());

        assertThat(EncodingUtils.toString(rsaEncodingUtils.convertKeyToBase64(
                rsaEncodingUtils.convertBase64ToPrivateKey(privateKey))), is(EncodingUtils.toString(privateKey)));
        assertThat(EncodingUtils.toString(rsaEncodingUtils.convertKeyToBase64(
                rsaEncodingUtils.convertBase64ToPublicKey(publicKey))), is(EncodingUtils.toString(publicKey)));

    }

    @Test
    public void assertThatEverytimeANewKeyIsGenerated() throws NoSuchAlgorithmException {
        KeyPair keyPair = rsaEncodingUtils.generateNewKeypair(2048);
        KeyPair keyPair2 = rsaEncodingUtils.generateNewKeypair(2048);

        assertThat(rsaEncodingUtils.convertKeyToBase64(keyPair.getPublic()),
                not(rsaEncodingUtils.convertKeyToBase64(keyPair2.getPublic())));
        assertThat(rsaEncodingUtils.convertKeyToBase64(keyPair.getPrivate()),
                not(rsaEncodingUtils.convertKeyToBase64(keyPair2.getPrivate())));
    }

    @Test
    public void assertNullsReturnNulls() throws InvalidKeySpecException, NoSuchAlgorithmException {
        assertNull(rsaEncodingUtils.convertKeyToBase64(null));
        assertNull(rsaEncodingUtils.convertBase64ToPrivateKey(null));
        assertNull(rsaEncodingUtils.convertBase64ToPublicKey(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenKeySizeIsNegativeThenGenerateNewKeypairThrowsIllegalArgumentException()
            throws NoSuchAlgorithmException {
        rsaEncodingUtils.generateNewKeypair(-1);
    }

}