package ro.rasel.utils.criptography.key;

import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Optional;

public class IVParameterSpecGenerator implements ParameterSpecGenerator {
    @Override
    public IvParameterSpec generateNewParameterSpec(int blockSize) {
        SecureRandom randomSecureRandom = new SecureRandom();
        byte[] iv = new byte[blockSize];
        randomSecureRandom.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public byte[] toBytes(IvParameterSpec parameterSpec) {
        return Optional.ofNullable(parameterSpec).map(IvParameterSpec::getIV).orElse(null);
    }

    public IvParameterSpec toParameterSpec(byte[] bytes) {
        return Optional.ofNullable(bytes).map(bytes1 -> new IvParameterSpec(bytes)).orElse(null);
    }
}