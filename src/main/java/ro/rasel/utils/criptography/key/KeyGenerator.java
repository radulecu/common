package ro.rasel.utils.criptography.key;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface KeyGenerator {
    String getAlgorithm();

    Key generateNewKey(int keySize) throws NoSuchAlgorithmException;

    default byte[] toBytes(Key key) {
        return Optional.ofNullable(key).map(Key::getEncoded).orElse(null);
    }

    SecretKey toKey(byte[] key);
}
