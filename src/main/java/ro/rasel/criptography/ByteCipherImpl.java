package ro.rasel.criptography;

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

public class ByteCipherImpl implements ByteCipher {
    private final CipherAlgorithm algorithm;

    public ByteCipherImpl(CipherAlgorithm algorithm) {
        Optional.ofNullable(algorithm).orElseThrow(() -> new IllegalArgumentException("algorithm must not be null"));

        this.algorithm = algorithm;
    }

    @Override
    public CipherAlgorithm getAlgorithm() {
        return null;
    }

    @Override
    public byte[] convert(byte[] message, CipherMode cipherMode, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        if (message == null) {
            return null;
        }

        return convert((cipher) -> cipher.doFinal(message), cipherMode, key);
    }

    @Override
    public byte[] convert(byte[] message, CipherMode cipherMode, Key key,
            AlgorithmParameterSpec algorithmParameterSpec)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        if (message == null) {
            return null;
        }

        return convert((cipher) -> cipher.doFinal(message), cipherMode, key, algorithmParameterSpec);
    }

    @Override
    public <T> T convert(CipherHandler<T> cipherHandler, CipherMode cypherMode, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        Objects.requireNonNull(cipherHandler, "cipherHandler argument must not be null");
        Cipher cipher = createCipher(key);
        cipher.init(cypherMode.getMode(), key);

        return cipherHandler.convert(cipher);
    }

    @Override
    public <T> T convert(CipherHandler<T> cipherHandler, CipherMode cypherMode, Key key,
            AlgorithmParameterSpec algorithmParameterSpec)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        Objects.requireNonNull(cipherHandler, "cipherHandler argument must not be null");
        Cipher cipher = createCipher(key);
        cipher.init(cypherMode.getMode(), key, algorithmParameterSpec);

        return cipherHandler.convert(cipher);
    }

    @Override
    public Cipher createCipher(Key key) throws NoSuchAlgorithmException, NoSuchPaddingException {
        Optional.ofNullable(key).orElseThrow(() -> new IllegalArgumentException("secretKey must not be null"));

        return Cipher.getInstance(algorithm.getCipherAlgorithm());
    }
}