package ro.rasel.utils.criptography.key;

import java.security.spec.KeySpec;

public interface KeySpecGenerator {
    PBEKeySpecAlgorithm getAlgorithm();

    KeySpec generateNewKeySpec(String text, byte[] seed);

    byte[] generateSeed(int seedLength);
}
