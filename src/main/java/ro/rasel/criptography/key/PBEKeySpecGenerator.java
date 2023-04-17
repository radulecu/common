package ro.rasel.criptography.key;

import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PBEKeySpecGenerator implements KeySpecGenerator {
    private final PBEKeySpecAlgorithm algorithm;
    private final SecureRandom secureRandom;

    public PBEKeySpecGenerator(PBEKeySpecAlgorithm algorithm) throws NoSuchAlgorithmException {
        this(algorithm, SecureRandom.getInstanceStrong());
    }

    public PBEKeySpecGenerator(PBEKeySpecAlgorithm algorithm, SecureRandom secureRandom) {
        this.algorithm = algorithm;
        this.secureRandom = secureRandom;
    }

    @Override
    public PBEKeySpecAlgorithm getAlgorithm() {
        return algorithm;
    }

    public PBEKeySpec generateNewKeySpec(String text, int seedLength) {
        return generateNewKeySpec(text, generateSeed(seedLength));
    }

    public PBEKeySpec generateNewKeySpec(String text, byte[] salt) {
        return new PBEKeySpec(text.toCharArray(), salt, algorithm.getIterations(),
                algorithm.getKeyLength());
    }

    @Override
    public byte[] generateSeed(int seedLength) {
        return secureRandom.generateSeed(seedLength);
    }

}