package ro.rasel.utils.criptography.key;

import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public interface KeyPairGenerator {
    String getAlgorithm();

    KeyPair generateNewKeyPair(int keySize) throws NoSuchAlgorithmException;

    byte[] toBytes(Key key);

    PrivateKey toPrivateKey(byte[] privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException;

    PublicKey toPublicKey(byte[] publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException;
}
