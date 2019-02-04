package ro.rasel.utils.criptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ro.rasel.utils.encoding.EncodingUtils;

import java.util.Arrays;
import java.util.Collection;

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
    public void assertThatConversionToBase64IsReversible() {
        String base64String = EncodingUtils.stringToBase64(text);
        byte[] base64Bytes = EncodingUtils.toBytes(base64String);
        byte[] textBytes = EncodingUtils.toBytes(text);

        assertThat(EncodingUtils.base64ToString(EncodingUtils.stringToBase64(text)), is(text));
        assertThat(EncodingUtils.stringToBase64(EncodingUtils.base64ToString(base64String)), is(base64String));
        assertThat(EncodingUtils.toString(EncodingUtils.bytesToBase64(EncodingUtils.base64ToBytes(base64Bytes))),
                is(base64String));
        assertThat(EncodingUtils.toString(EncodingUtils.base64ToBytes(EncodingUtils.bytesToBase64(textBytes))),
                is(text));
    }

    @Test
    public void assertNullsReturnNulls() {
        assertNull(EncodingUtils.base64ToBytes(null));
        assertNull(EncodingUtils.bytesToBase64(null));
        assertNull(EncodingUtils.base64ToString(null));
        assertNull(EncodingUtils.stringToBase64(null));
    }

    @Test
    public void assertConversionBetweenBytesAndStringIsReversible() {
        assertThat(EncodingUtils.toString(EncodingUtils.toBytes(text)), is(text));
        // check conversion returns String from pool and has the exact same object as original text
        assertThat(EncodingUtils.toString(EncodingUtils.toBytes(text)), is(text));
    }
}