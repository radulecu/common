package ro.rasel.encoding;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;

public class EncodingUtils {
    public static byte[] bytesToBase64(byte[] bytes) {
        return Optional.ofNullable(bytes).map(b -> Base64.getEncoder().encode(b)).orElse(null);
    }

    public static byte[] base64ToBytes(byte[] encoded) {
        return Optional.ofNullable(encoded).map(b -> Base64.getDecoder().decode(b)).orElse(null);
    }

    public static String stringToBase64(String value) {
        return Optional.ofNullable(value).map(s -> toString(Base64.getEncoder().encode(toBytes(s)))).orElse(null);
    }

    public static String base64ToString(String encoded) {
        return Optional.ofNullable(encoded).map(s -> toString(Base64.getDecoder().decode(s))).orElse(null);
    }

    public static String toString(byte[] bytes) {
        return toString(bytes, Charset.defaultCharset()).orElse(null);
    }

    public static Optional<String> toString(byte[] bytes, Charset charset) {
        return Optional.ofNullable(bytes).map(bytes1 -> new String(bytes1, charset).intern());
    }

    public static byte[] toBytes(String string) {
        return toBytes(string, Charset.defaultCharset());
    }

    private static byte[] toBytes(String string, Charset charset) {
        return Optional.ofNullable(string).map(s -> s.getBytes(charset)).orElse(null);
    }
}