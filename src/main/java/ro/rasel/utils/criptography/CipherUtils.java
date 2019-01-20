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
import java.util.Objects;
import java.util.Optional;

public class CipherUtils {
    private final ICipherAlgorithm algorithm;

    public CipherUtils(ICipherAlgorithm algorithm) {
        Optional.of(algorithm).orElseThrow(() -> new IllegalArgumentException("algorithm must not be null"));

        this.algorithm = algorithm;
    }

    public ICipherAlgorithm getAlgorithm() {
        return algorithm;
    }

    public byte[] convert(byte[] message, CipherMode cipherMode, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        if (message == null) {
            return null;
        }

        return convert((cipher) -> cipher.doFinal(message), cipherMode, key);
    }

    public <T> T convert(EncryptionHandler<T> encryptionHandler, CipherMode cypherMode, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        Objects.requireNonNull(encryptionHandler, "encryptionHandler argument must not be null");
        Cipher cipher = createCipher(key);
        cipher.init(cypherMode.getMode(), key);

        return encryptionHandler.convert(cipher);
    }

    public byte[] convert(byte[] message, CipherMode cipherMode, Key key,
            AlgorithmParameterSpec algorithmParameterSpec)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        if (message == null) {
            return null;
        }

        return convert((cipher) -> cipher.doFinal(message), cipherMode, key, algorithmParameterSpec);
    }

    public <T> T convert(EncryptionHandler<T> encryptionHandler, CipherMode cypherMode, Key key,
            AlgorithmParameterSpec algorithmParameterSpec)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        Objects.requireNonNull(encryptionHandler, "encryptionHandler argument must not be null");
        Cipher cipher = createCipher(key);
        cipher.init(cypherMode.getMode(), key, algorithmParameterSpec);

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