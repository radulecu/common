package ro.rasel.utils.criptography;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Optional;

public class CipherUtils {
    private final ICipherAlgorithm algorithm;

    public CipherUtils(ICipherAlgorithm algorithm) {
        Optional.of(algorithm).orElseThrow(() -> new IllegalArgumentException("algorithm must not be null"));

        this.algorithm = algorithm;
    }

    public byte[] encryptMessage(byte[] message, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        if (message == null) {
            return null;
        }

        return encryptMessage(key, (cipher) -> cipher.doFinal(message));
    }

    public <T> T encryptMessage(Key key, EncryptionHandler<T> encryptionHandler)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        return convert(key, Cipher.ENCRYPT_MODE, encryptionHandler);
    }

    public byte[] encryptMessage(byte[] message, Key key, AlgorithmParameterSpec algorithmParameterSpec)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        if (message == null) {
            return null;
        }

        return encryptMessage(key, algorithmParameterSpec, (cipher) -> cipher.doFinal(message));
    }

    public <T> T encryptMessage(Key key, AlgorithmParameterSpec algorithmParameterSpec,
                                EncryptionHandler<T> encryptionHandler)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        return convert(key, Cipher.ENCRYPT_MODE, algorithmParameterSpec, encryptionHandler);
    }

    public byte[] decryptMessage(byte[] encryptedMessage, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        if (encryptedMessage == null) {
            return null;
        }

        return decryptMessage(key, (cipher) -> cipher.doFinal(encryptedMessage));
    }

    public <T> T decryptMessage(Key key, EncryptionHandler<T> encryptionHandler)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        return convert(key, Cipher.DECRYPT_MODE, encryptionHandler);
    }

    public byte[] decryptMessage(byte[] encryptedMessage, Key key, AlgorithmParameterSpec algorithmParameterSpec)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        if (encryptedMessage == null) {
            return null;
        }

        return decryptMessage(key, algorithmParameterSpec, (cipher) -> cipher.doFinal(encryptedMessage));
    }

    public <T> T decryptMessage(Key key, AlgorithmParameterSpec algorithmParameterSpec,
                                EncryptionHandler<T> encryptionHandler)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        return convert(key, Cipher.DECRYPT_MODE, algorithmParameterSpec, encryptionHandler);
    }

    private <T> T convert(Key key, int mode, EncryptionHandler<T> encryptionHandler)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        Cipher cipher = createCipher(key);
        cipher.init(mode, key);

        return encryptionHandler.convert(cipher);
    }

    private <T> T convert(Key key, int mode, AlgorithmParameterSpec algorithmParameterSpec,
                          EncryptionHandler<T> encryptionHandler)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = createCipher(key);
        cipher.init(mode, key, algorithmParameterSpec);

        return encryptionHandler.convert(cipher);
    }

    private Cipher createCipher(Key key) throws NoSuchAlgorithmException, NoSuchPaddingException {
        Optional.of(key).orElseThrow(() -> new IllegalArgumentException("secretKey must not be null"));

        return Cipher.getInstance(algorithm.getCipherAlgorithm());
    }

    @FunctionalInterface
    public interface EncryptionHandler<T> {
        T convert(Cipher cipher) throws IllegalBlockSizeException, BadPaddingException;
    }
}