package ro.rasel.time.format;

import org.junit.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DurationFormatterTest {

    private static final int NANOS = 345;
    private static final int MICROS = 12;
    private static final int MILLIS = 25;
    private static final int SECONDS = 5;
    private static final int MINUTES = 7;
    private static final int HOURS = 9;
    private static final int DAYS = 3;

    private static final Duration DURATION =
            Duration.ofNanos(NANOS + MICROS * 1000).plusMillis(MILLIS).plusSeconds(SECONDS).plusMinutes(MINUTES)
                    .plusHours(HOURS).plusDays(DAYS);
    private static final String DURATION_AS_STRING =
            String.format("%dd:%sh:%sm:%ss:%sms:%sus:%sns", DAYS, HOURS, MINUTES, SECONDS, MILLIS, MICROS, NANOS);
    private static final String DURATION_AS_STRING_VERBOSE =
            String.format("%d days:%s hours:%s minutes:%s seconds:%s milliseconds:%s microseconds:%s nanoseconds", DAYS,
                    HOURS, MINUTES, SECONDS, MILLIS, MICROS, NANOS);

    @Test
    public void testGetters() {
        ITemporalAmountFormatter<Duration, ChronoUnit> durationFormatter = new DurationFormatter(DURATION);
        assertThat(durationFormatter.getAsInt(ChronoUnit.NANOS), is(NANOS));
        assertThat(durationFormatter.getAsInt(ChronoUnit.MICROS), is(MICROS));
        assertThat(durationFormatter.getAsInt(ChronoUnit.MILLIS), is(MILLIS));
        assertThat(durationFormatter.getAsInt(ChronoUnit.DAYS), is(DAYS));
        assertThat(durationFormatter.getAsInt(ChronoUnit.MINUTES), is(MINUTES));
        assertThat(durationFormatter.getAsInt(ChronoUnit.HOURS), is(HOURS));
        assertThat(durationFormatter.getAsInt(ChronoUnit.SECONDS), is(SECONDS));
    }

    @Test
    public void testToString() {
        ITemporalAmountFormatter<Duration, ChronoUnit> durationFormatterNonVerbose =
                new DurationFormatter(DURATION, false);
        ITemporalAmountFormatter<Duration, ChronoUnit> durationFormatterVerbose = new DurationFormatter(DURATION, true);

        assertThat(durationFormatterNonVerbose.toString(), is(DURATION_AS_STRING));
        assertThat(durationFormatterVerbose.toString(), is(DURATION_AS_STRING_VERBOSE));

        assertThat(DurationFormatter.of(DURATION_AS_STRING).getTime(), is(DURATION));
        assertThat(DurationFormatter.of(DURATION_AS_STRING_VERBOSE, true).getTime(), is(DURATION));
    }

    @Test
    public void testZeroValues() {
        final Duration durationOfZeros = Duration.ofDays(0);
        final String durationOfZerosAsString = "0";

        ITemporalAmountFormatter<Duration, ChronoUnit> durationFormatterNonVerbose =
                new DurationFormatter(durationOfZeros, false);
        ITemporalAmountFormatter<Duration, ChronoUnit> durationFormatterVerbose =
                new DurationFormatter(durationOfZeros, true);

        assertThat(durationFormatterNonVerbose.toString(), is(durationOfZerosAsString));
        assertThat(durationFormatterVerbose.toString(), is(durationOfZerosAsString));

        assertThat(DurationFormatter.of(durationOfZerosAsString).getTime(), is(durationOfZeros));
        assertThat(DurationFormatter.of(durationOfZerosAsString, true).getTime(), is(durationOfZeros));
    }

    @Test
    public void testOnesValues() {
        Duration durationOfOnes =
                Duration.ofNanos(1).plusMillis(1).plusSeconds(1).plusMinutes(1).plusHours(1).plusDays(1);
        final String durationOfOnesAsString = "1d:1h:1m:1s:1ms:1ns";
        final String durationOfOnesAsStringVerbose = "1 day:1 hour:1 minute:1 second:1 millisecond:1 nanosecond";

        ITemporalAmountFormatter<Duration, ChronoUnit> durationFormatterNonVerbose =
                new DurationFormatter(durationOfOnes, false);
        ITemporalAmountFormatter<Duration, ChronoUnit> durationFormatterVerbose =
                new DurationFormatter(durationOfOnes, true);

        assertThat(durationFormatterNonVerbose.toString(), is(durationOfOnesAsString));
        assertThat(durationFormatterVerbose.toString(), is(durationOfOnesAsStringVerbose));

        assertThat(DurationFormatter.of(durationOfOnesAsString).getTime(), is(durationOfOnes));
        assertThat(DurationFormatter.of(durationOfOnesAsStringVerbose, true).getTime(), is(durationOfOnes));
    }

    @Test
    public void testPartialValues() {
        Map<Duration, String> expectedResults = new HashMap<>();
        Map<Duration, String> expectedVerboseResults = new HashMap<>();

        Duration duration = Duration.ofNanos(NANOS);
        String durationAsString = NANOS + "ns";
        String durationAsStringVerbose = NANOS + " nanoseconds";
        assertThat(new DurationFormatter(duration, false).toString(), is(durationAsString));
        assertThat(new DurationFormatter(duration, true).toString(), is(durationAsStringVerbose));
        assertThat(DurationFormatter.of(durationAsString).getTime(), is(duration));
        assertThat(DurationFormatter.of(durationAsStringVerbose, true).getTime(), is(duration));

        duration = duration.plusMinutes(MINUTES);
        durationAsString = MINUTES + "m" + ":" + NANOS + "ns";
        durationAsStringVerbose = MINUTES + " minutes" + ":" + NANOS + " nanoseconds";
        assertThat(new DurationFormatter(duration, false).toString(), is(durationAsString));
        assertThat(new DurationFormatter(duration, true).toString(), is(durationAsStringVerbose));
        assertThat(DurationFormatter.of(durationAsString).getTime(), is(duration));
        assertThat(DurationFormatter.of(durationAsStringVerbose, true).getTime(), is(duration));

        duration = duration.plusHours(HOURS);
        durationAsString = HOURS + "h" + ":" + MINUTES + "m" + ":" + NANOS + "ns";
        durationAsStringVerbose = HOURS + " hours" + ":" + MINUTES + " minutes" + ":" + NANOS + " nanoseconds";
        assertThat(new DurationFormatter(duration, false).toString(), is(durationAsString));
        assertThat(new DurationFormatter(duration, true).toString(), is(durationAsStringVerbose));
        assertThat(DurationFormatter.of(durationAsString).getTime(), is(duration));
        assertThat(DurationFormatter.of(durationAsStringVerbose, true).getTime(), is(duration));
    }

    @Test(expected = RuntimeException.class)
    public void testDaysValueToBig() {
        ITemporalAmountFormatter<Duration, ChronoUnit> durationFormatter =
                new DurationFormatter(Duration.ofDays(Integer.MAX_VALUE + 1L));
        durationFormatter.getAsInt(ChronoUnit.DAYS);
    }

}