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
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        int mode = Cipher.ENCRYPT_MODE;
        return convert(message, key, mode, (cipher) -> cipher.doFinal(message));
    }

    public byte[] encryptMessage(byte[] message, Key key, AlgorithmParameterSpec algorithmParameterSpec)
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        int mode = Cipher.ENCRYPT_MODE;
        return convert(message, key, mode, algorithmParameterSpec, (cipher) -> cipher.doFinal(message));
    }

    public byte[] decryptMessage(byte[] encryptedMessage, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        int mode = Cipher.DECRYPT_MODE;
        return convert(encryptedMessage, key, mode, (cipher) -> cipher.doFinal(encryptedMessage));
    }

    public byte[] decryptMessage(byte[] encryptedMessage, Key key, AlgorithmParameterSpec algorithmParameterSpec)
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        int mode = Cipher.DECRYPT_MODE;
        return convert(encryptedMessage, key, mode, algorithmParameterSpec, (cipher) -> cipher.doFinal(encryptedMessage));
    }

    private byte[] convert(byte[] input, Key key, int mode, EncryptionHandler<byte[]> encryptionHandler)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, InvalidAlgorithmParameterException {
        if (input == null) {
            return null;
        }

        Cipher cipher = createCipher(key);
        cipher.init(mode, key);

        return encryptionHandler.convert(cipher);
    }

    private byte[] convert(byte[] input, Key key, int mode, AlgorithmParameterSpec algorithmParameterSpec,
                           EncryptionHandler<byte[]> encryptionHandler)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, InvalidAlgorithmParameterException {
        if (input == null) {
            return null;
        }

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
        T convert(Cipher cipher)
                throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
                IllegalBlockSizeException, InvalidAlgorithmParameterException;
    }
}