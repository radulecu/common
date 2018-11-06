package ro.rasel.utils.criptography;

import javax.crypto.*;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class EncryptionUtils {
    private final String algorithm;

    public EncryptionUtils(String algorithm) {
        Optional.of(algorithm).orElseThrow(() -> new IllegalArgumentException("algorithm must not be null"));

        this.algorithm = algorithm;
    }

    public byte[] encryptMessage(byte[] message, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException {
        int mode = Cipher.ENCRYPT_MODE;
        return convert(message, key, mode);
    }

    public byte[] decryptMessage(byte[] encriptedMessage, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException {
        int mode = Cipher.DECRYPT_MODE;
        return convert(encriptedMessage, key, mode);
    }

    public void encryptMessage(ByteBuffer message, ByteBuffer result, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, ShortBufferException {
        int mode = Cipher.ENCRYPT_MODE;
        convert(message, result, key, mode);
    }

    public void decryptMessage(ByteBuffer encriptedMessage, ByteBuffer result, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, ShortBufferException {
        int mode = Cipher.DECRYPT_MODE;
        convert(encriptedMessage, result, key, mode);
    }

    private byte[] convert(byte[] message, Key key, int mode)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        if (message == null) {
            return null;
        }

        Cipher cipher = createCipher(key, mode);

        return cipher.doFinal(message);
    }

    private void convert(ByteBuffer messageBuffer, ByteBuffer resultBuffer, Key key, int mode)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, ShortBufferException {
        Optional.of(messageBuffer).orElseThrow(() -> new IllegalArgumentException("messageBuffer must not be null"));
        Optional.of(resultBuffer).orElseThrow(() -> new IllegalArgumentException("resultBuffer must not be null"));

        Cipher cipher = createCipher(key, mode);

        cipher.doFinal(messageBuffer, resultBuffer);
    }

    private Cipher createCipher(Key key, int mode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Optional.of(key).orElseThrow(() -> new IllegalArgumentException("secretKey must not be null"));
        System.out.println(algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(mode, key);
        return cipher;
    }
}
