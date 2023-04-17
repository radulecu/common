package ro.rasel.hashing;

import java.security.spec.InvalidKeySpecException;

public interface HashGenerator {
    HashingAlgorithm getHashingAlgorithm();

    Hash generateHash(String text) throws InvalidKeySpecException;

    byte[] generateHash(String text, byte[] salt) throws InvalidKeySpecException;

    byte[] generateSalt();

}