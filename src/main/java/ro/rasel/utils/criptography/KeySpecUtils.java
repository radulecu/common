package ro.rasel.utils.criptography;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;

public class KeySpecUtils {
    private final String algorithm;

    public KeySpecUtils(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public Key generateNewKeySpec(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(keySize);
        return keyGenerator.generateKey();
    }

    public IvParameterSpec generateNewIvSpec(int blockSize) {
        SecureRandom randomSecureRandom = new SecureRandom();
        byte[] iv = new byte[blockSize];
        randomSecureRandom.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public byte[] toBytes(Key key) {
        return Optional.ofNullable(key).map(Key::getEncoded).orElse(null);
    }

    public Key toKey(byte[] key) {
        if (key == null) {
            return null;
        }
        return new SecretKeySpec(key, algorithm);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeySpecUtils that = (KeySpecUtils) o;
        return Objects.equals(algorithm, that.algorithm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm);
    }

    @Override
    public String toString() {
        return "KeyPairUtils{" + "algorithm='" + algorithm + '\'' + '}';
    }
}