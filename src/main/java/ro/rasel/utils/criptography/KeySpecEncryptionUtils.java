package ro.rasel.utils.criptography;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;
import java.util.Optional;

public class KeySpecEncryptionUtils {
    private final String algorithm;

    public KeySpecEncryptionUtils(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public KeySpec generateNewKeySpec(int keySize) throws NoSuchAlgorithmException {
        if (keySize <= 0) {
            throw new IllegalArgumentException("keySize must be positive");
        }

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    public byte[] toBytes(Key key) {
        return Optional.ofNullable(key).map(k -> k.getEncoded()).orElse(null);
    }

    public PrivateKey toPrivateKey(byte[] privateKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (privateKey == null) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
    }

    public PublicKey toPublicKey(byte[] publicKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (publicKey == null) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeySpecEncryptionUtils that = (KeySpecEncryptionUtils) o;
        return Objects.equals(algorithm, that.algorithm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm);
    }

    @Override
    public String toString() {
        return "KeyPairEncryptionUtils{" +
                "algorithm='" + algorithm + '\'' +
                '}';
    }
}
