package ro.rasel.utils.criptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class EncodingUtilsTest {
    private final String text;

    public EncodingUtilsTest(String text) {
        this.text = text;
    }

    @Parameters
    public static Collection<String[]> data() {
        return Arrays.asList(new String[][]{{"test"}, {"(teststring)*(^*&787867"}, {null}, {""}});
    }

    @Test
    public void assertThatConversionOfBytesToBase64IsReversible() {
        String base64String = EncodingUtils.encodeStringToBase64String(text);
        byte[] base64Bytes = text != null ? text.getBytes() : null;

        assertThat(EncodingUtils.decodeStringFromBase64String(
                EncodingUtils.encodeStringToBase64String(text)), is(text));

        assertThat(EncodingUtils.encodeStringToBase64String(
                EncodingUtils.decodeStringFromBase64String(base64String)), is(base64String));

        assertThat(EncodingUtils.encodeBytesToBase64String(
                EncodingUtils.decodeBytesFromBase64String(base64String)), is(base64String));

        assertThat(Objects.deepEquals(
                EncodingUtils.decodeBytesFromBase64String(EncodingUtils.encodeBytesToBase64String(base64Bytes)),
                base64Bytes), is(true));
    }

    @Test
    public void assertNullsReturnNulls() {
        assertNull(EncodingUtils.decodeBytesFromBase64String(null));
        assertNull(EncodingUtils.encodeBytesToBase64String(null));
        assertNull(EncodingUtils.decodeStringFromBase64String(null));
        assertNull(EncodingUtils.encodeStringToBase64String(null));
    }
}