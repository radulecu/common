package ro.rasel.utils.criptography;

import java.util.Base64;

public class EncodingUtils {
    public static String encodeBytesToBase64String(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] decodeBytesFromBase64String(String encoded) {
        return Base64.getDecoder().decode(encoded);
    }
}
