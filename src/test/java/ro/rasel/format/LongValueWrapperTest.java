package ro.rasel.format;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class LongValueWrapperTest {
    @Test
    public void wrapperTest() {
        LongValueFormatter wrappedLongValue =
                new LongValueFormatter("prefix", "suffix", "pluralSuffix");
        assertThat(wrappedLongValue.parse("prefix9suffix"), is(9L));
        assertThat(wrappedLongValue.parse("prefix9pluralSuffix"), is(9L));
        assertThat(wrappedLongValue.parse(" prefix9suffix "), is(9L));
        assertThat(wrappedLongValue.parse(" prefix9pluralSuffix "), is(9L));
        assertThat(wrappedLongValue.parse("prefix 9 suffix"), is(9L));
        assertThat(wrappedLongValue.parse("prefix 9 pluralSuffix"), is(9L));
        assertThat(wrappedLongValue.parse(" prefix 9 suffix "), is(9L));
        assertThat(wrappedLongValue.parse(" prefix 9 pluralSuffix "), is(9L));

        wrappedLongValue = new LongValueFormatter("prefix ", " suffix", " pluralSuffix");
        assertNull(wrappedLongValue.parse("prefix9suffix"));
        assertNull(wrappedLongValue.parse("prefix9pluralSuffix"));
        assertNull(wrappedLongValue.parse(" prefix9suffix "));
        assertNull(wrappedLongValue.parse(" prefix9pluralSuffix "));
        assertThat(wrappedLongValue.parse("prefix 9 suffix"), is(9L));
        assertThat(wrappedLongValue.parse("prefix 9 pluralSuffix"), is(9L));
        assertThat(wrappedLongValue.parse(" prefix 9 pluralSuffix "), is(9L));
        assertThat(wrappedLongValue.parse(" prefix 9 pluralSuffix "), is(9L));
    }

}