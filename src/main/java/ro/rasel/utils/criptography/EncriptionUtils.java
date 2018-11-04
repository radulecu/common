package ro.rasel.utils.criptography;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

public class EncriptionUtils {
    private final String algorithm;

    public EncriptionUtils(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public KeyPair generateNewKeypair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    public String convertKeyToBase64(Key publicKey) {
        return EncodingUtils.encodeBytesToBase64String(publicKey.getEncoded());
    }

    public PrivateKey convertPrivateKeyFromBase64String(String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(EncodingUtils.decodeBytesFromBase64String(privateKey)));
    }

    public PublicKey convertPublicKeyFromBase64String(String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(new X509EncodedKeySpec(EncodingUtils.decodeBytesFromBase64String(publicKey)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncriptionUtils that = (EncriptionUtils) o;
        return Objects.equals(algorithm, that.algorithm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm);
    }

    @Override
    public String toString() {
        return "EncriptionUtils{" +
                "algorithm='" + algorithm + '\'' +
                '}';
    }
}
