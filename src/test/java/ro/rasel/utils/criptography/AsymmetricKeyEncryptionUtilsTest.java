package ro.rasel.utils.criptography;

import org.junit.Test;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class AsymmetricKeyEncryptionUtilsTest {
    private final AsymmetricKeyEncryptionUtils rsaEncodingUtils = new AsymmetricKeyEncryptionUtils("RSA");

    @Test
    public void assertThatConversionOfKeysToBase64IsReversible()
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyPair keyPair = rsaEncodingUtils.generateNewKeypair(2048);

        String privateKey = rsaEncodingUtils.convertKeyToBase64(keyPair.getPrivate());
        String publicKey = rsaEncodingUtils.convertKeyToBase64(keyPair.getPublic());

        assertThat(rsaEncodingUtils.convertKeyToBase64(
                rsaEncodingUtils.convertBase64ToPrivateKey(privateKey)), is(privateKey));
        assertThat(rsaEncodingUtils.convertKeyToBase64(
                rsaEncodingUtils.convertBase64ToPublicKey(publicKey)), is(publicKey));

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