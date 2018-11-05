package ro.rasel.utils.criptography;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;
import java.util.Optional;

public class AsymmetricKeyEncryptionUtils {
    private final String algorithm;

    public AsymmetricKeyEncryptionUtils(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public KeyPair generateNewKeypair(int keySize) throws NoSuchAlgorithmException {
        if (keySize <= 0) {
            throw new IllegalArgumentException("keySize must be positive");
        }

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    public String convertKeyToBase64(Key publicKey) {
        return Optional.ofNullable(publicKey)
                .map(k -> EncodingUtils.toString(EncodingUtils.bytesToBase64(k.getEncoded())))
                .orElse(null);
    }

    public PrivateKey convertBase64ToPrivateKey(String privateKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (privateKey == null) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory
                .generatePrivate(new PKCS8EncodedKeySpec(
                        EncodingUtils.base64ToBytes(EncodingUtils.toBytes(privateKey))));
    }

    public PublicKey convertBase64ToPublicKey(String publicKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (publicKey == null) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(
                new X509EncodedKeySpec(EncodingUtils.base64ToBytes(EncodingUtils.toBytes(publicKey))));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsymmetricKeyEncryptionUtils that = (AsymmetricKeyEncryptionUtils) o;
        return Objects.equals(algorithm, that.algorithm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm);
    }

    @Override
    public String toString() {
        return "AsymmetricKeyEncryptionUtils{" +
                "algorithm='" + algorithm + '\'' +
                '}';
    }
}
