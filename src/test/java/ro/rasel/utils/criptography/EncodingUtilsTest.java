package ro.rasel.utils.criptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

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
    public void assertThatConversionOfBytesToBase64IsReversible() {
        String base64 = EncodingUtils.encodeStringToBase64String(text);
        assertThat(EncodingUtils.encodeBytesToBase64String(
                EncodingUtils.decodeBytesFromBase64String(base64)), is(base64));
    }

    @Test
    public void assertNullsReturnNulls() {
        assertNull(EncodingUtils.decodeBytesFromBase64String(null));
        assertNull(EncodingUtils.encodeBytesToBase64String(null));
        assertNull(EncodingUtils.decodeStringFromBase64String(null));
        assertNull(EncodingUtils.encodeStringToBase64String(null));
    }
}