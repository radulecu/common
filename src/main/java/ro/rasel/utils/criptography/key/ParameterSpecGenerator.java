package ro.rasel.utils.criptography.key;

import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

public interface ParameterSpecGenerator {
    AlgorithmParameterSpec generateNewParameterSpec(int keySize) throws NoSuchAlgorithmException;
}
