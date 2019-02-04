package ro.rasel.utils.criptography.key;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

public class SecretKeyGenerator implements KeyGenerator {
    private final String algorithm;

    public SecretKeyGenerator(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String getAlgorithm() {
        return algorithm;
    }

    @Override
    public SecretKey generateNewKey(int keySize) throws NoSuchAlgorithmException {
        javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance(algorithm);
        keyGenerator.init(keySize);
        return keyGenerator.generateKey();
    }

    @Override
    public SecretKey toKey(byte[] key) {
        return Optional.ofNullable(key).map(bytes -> new SecretKeySpec(key, algorithm)).orElse(null);
    }

    @Override
    public byte[] toBytes(Key key) {
        return Optional.ofNullable(key).map(Key::getEncoded).orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SecretKeyGenerator that = (SecretKeyGenerator) o;
        return Objects.equals(algorithm, that.algorithm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm);
    }

    @Override
    public String toString() {
        return "SecretKeyGenerator{" +
                "algorithm='" + algorithm + '\'' +
                '}';
    }
}