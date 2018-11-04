package ro.rasel.utils.criptography;

import java.util.Base64;
import java.util.Optional;

public class EncodingUtils {
    public static String encodeBytesToBase64String(byte[] bytes) {
        return Optional.ofNullable(bytes).map(b -> Base64.getEncoder().encodeToString(b)).orElse(null);
    }

    public static byte[] decodeBytesFromBase64String(String encoded) {
        return Optional.ofNullable(encoded).map(s -> Base64.getDecoder().decode(s)).orElse(null);
    }

    public static String encodeStringToBase64String(String value) {
        return Optional.ofNullable(value).map(s -> encodeBytesToBase64String(s.getBytes())).orElse(null);

    }

    public static String decodeStringFromBase64String(String encoded) {
        return Optional.ofNullable(encoded).map(s -> new String(Base64.getDecoder().decode(s)).intern()).orElse(null);
    }
}
