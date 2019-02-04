package ro.rasel.utils.hashing;

import ro.rasel.utils.criptography.key.KeySpecGenerator;

import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Optional;

public class SecretKeyFactoryHashGenerator implements HashGenerator {
    private final SecretKeyFactory secretKeyFactory;
    private final HashingAlgorithm hashingAlgorithm;
    private final KeySpecGenerator keySpecGenerator;

    public SecretKeyFactoryHashGenerator(HashingAlgorithm hashingAlgorithm, KeySpecGenerator keySpecGenerator)
            throws NoSuchAlgorithmException {
        this.hashingAlgorithm = Optional.ofNullable(hashingAlgorithm).orElseThrow(
                () -> new IllegalArgumentException("hashingAlgorithm must not be null"));
        this.keySpecGenerator = Optional.ofNullable(keySpecGenerator).orElseThrow(
                () -> new IllegalArgumentException("keySpecGenerator must not be null"));
        this.secretKeyFactory = SecretKeyFactory.getInstance(hashingAlgorithm.getAlgorithm());
    }

    @Override
    public HashingAlgorithm getHashingAlgorithm() {
        return hashingAlgorithm;
    }

    @Override
    public Hash generateHash(String text) throws InvalidKeySpecException {
        if (text == null) {
            return null;
        }
        final byte[] salt = generateSalt();
        return new HashImpl(generateHash(text, salt), salt);
    }

    @Override
    public byte[] generateHash(String text, byte[] salt) throws InvalidKeySpecException {
        Optional.ofNullable(salt).orElseThrow(() -> new IllegalArgumentException("salt should not be null"));
        if (text == null) {
            return null;
        }
        KeySpec keySpec = keySpecGenerator.generateNewKeySpec(text, salt);
        return secretKeyFactory.generateSecret(keySpec).getEncoded();
    }

    @Override
    public byte[] generateSalt() {
        return keySpecGenerator.generateSeed(hashingAlgorithm.getSaltLength());
    }
}
