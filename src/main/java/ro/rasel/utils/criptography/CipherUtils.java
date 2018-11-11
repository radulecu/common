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
            InvalidKeyException {
        int mode = Cipher.ENCRYPT_MODE;
        return convert(message, key, mode);
    }

    public byte[] encryptMessage(byte[] message, Key key, AlgorithmParameterSpec algorithmParameterSpec)
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        int mode = Cipher.ENCRYPT_MODE;
        return convert(message, key, algorithmParameterSpec, mode);
    }

    public byte[] decryptMessage(byte[] encriptedMessage, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException {
        int mode = Cipher.DECRYPT_MODE;
        return convert(encriptedMessage, key, mode);
    }

    public byte[] decryptMessage(byte[] encriptedMessage, Key key, AlgorithmParameterSpec algorithmParameterSpec)
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        int mode = Cipher.DECRYPT_MODE;
        return convert(encriptedMessage, key, algorithmParameterSpec, mode);
    }

    private byte[] convert(byte[] message, Key key, int mode)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        if (message == null) {
            return null;
        }

        Cipher cipher = createCipher(key);
        cipher.init(mode, key);

        return cipher.doFinal(message);
    }

    private byte[] convert(byte[] message, Key key, AlgorithmParameterSpec algorithmParameterSpec, int mode)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, InvalidAlgorithmParameterException {
        if (message == null) {
            return null;
        }

        Cipher cipher = createCipher(key);
        cipher.init(mode, key, algorithmParameterSpec);

        return cipher.doFinal(message);
    }

    private Cipher createCipher(Key key) throws NoSuchAlgorithmException, NoSuchPaddingException {
        Optional.of(key).orElseThrow(() -> new IllegalArgumentException("secretKey must not be null"));

        Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
        return cipher;
    }
}