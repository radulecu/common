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

public interface ByteCipher {
    CipherAlgorithm getAlgorithm();

    byte[] convert(byte[] message, CipherMode cipherMode, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException;

    byte[] convert(byte[] message, CipherMode cipherMode, Key key,
            AlgorithmParameterSpec algorithmParameterSpec)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException;

    <T2> T2 convert(CipherHandler<T2> cipherHandler, CipherMode cypherMode, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException;

    <T2> T2 convert(CipherHandler<T2> cipherHandler, CipherMode cypherMode, Key key,
            AlgorithmParameterSpec algorithmParameterSpec)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException;

    Cipher createCipher(Key key) throws NoSuchAlgorithmException, NoSuchPaddingException;

    @FunctionalInterface
    interface CipherHandler<T> {
        T convert(Cipher cipher) throws IllegalBlockSizeException, BadPaddingException;
    }
}
