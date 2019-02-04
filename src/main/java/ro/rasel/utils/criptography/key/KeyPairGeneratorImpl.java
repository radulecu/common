package ro.rasel.utils.criptography.key;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;
import java.util.Optional;

public class KeyPairGeneratorImpl implements KeyPairGenerator {
    private final String algorithm;

    public KeyPairGeneratorImpl(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String getAlgorithm() {
        return algorithm;
    }

    @Override
    public KeyPair generateNewKeyPair(int keySize) throws NoSuchAlgorithmException {
        if (keySize <= 0) {
            throw new IllegalArgumentException("keySize must be positive");
        }

        java.security.KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    @Override
    public byte[] toBytes(Key key) {
        return Optional.ofNullable(key).map(Key::getEncoded).orElse(null);
    }

    @Override
    public PrivateKey toPrivateKey(byte[] privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (privateKey == null) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
    }

    @Override
    public PublicKey toPublicKey(byte[] publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (publicKey == null) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyPairGeneratorImpl that = (KeyPairGeneratorImpl) o;
        return Objects.equals(algorithm, that.algorithm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm);
    }

    @Override
    public String toString() {
        return "KeyPairGeneratorImpl{" +
                "algorithm='" + algorithm + '\'' +
                '}';
    }
}