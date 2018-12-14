package ro.rasel.time;

import org.junit.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TimeIntervalTest {

    private static final int NANOS = 12345;
    private static final int MILLIS = 25;
    private static final int SECONDS = 5;
    private static final int MINUTES = 7;
    private static final int HOURS = 9;
    private static final long DAYS = 3;

    private static final Duration DURATION = Duration.ofNanos(NANOS)
            .plusMillis(MILLIS).plusSeconds(SECONDS).plusMinutes(MINUTES).plusHours(HOURS)
            .plusDays(DAYS);
    private static final String DURATION_AS_STRING =
            String.format("%dd:%sh:%sm:%ss:%sms:%sns", DAYS, HOURS, MINUTES, SECONDS, MILLIS, NANOS);
    private static final String DURATION_AS_STRING_VERBOSE =
            String.format("%ddays:%shours:%sminutes:%sseconds:%smilliseconds:%snanoseconds", DAYS, HOURS, MINUTES,
                    SECONDS, MILLIS, NANOS);

    @Test
    public void testGetters() {
        ITimeInterval timeInterval = new TimeInterval(DURATION);
        assertThat(timeInterval.getNanoseconds(), is(NANOS));
        assertThat(timeInterval.getMilliseconds(), is(MILLIS));
        assertThat(timeInterval.getDays(), is(DAYS));
        assertThat(timeInterval.getMinutes(), is(MINUTES));
        assertThat(timeInterval.getHours(), is(HOURS));
        assertThat(timeInterval.getSeconds(), is(SECONDS));
    }

    @Test
    public void testToString() {
        ITimeInterval timeIntervalNonVerbose = new TimeInterval(DURATION, false);
        ITimeInterval timeIntervalVerbose = new TimeInterval(DURATION, true);

        assertThat(timeIntervalNonVerbose.toString(), is(DURATION_AS_STRING));
        assertThat(timeIntervalVerbose.toString(), is(DURATION_AS_STRING_VERBOSE));
    }

    @Test
    public void testZeroValues() {
        ITimeInterval timeIntervalNonVerbose = new TimeInterval(Duration.ofSeconds(0), false);
        ITimeInterval timeIntervalVerbose = new TimeInterval(Duration.ofSeconds(0), true);

        assertThat(timeIntervalNonVerbose.toString(), is("0"));
        assertThat(timeIntervalVerbose.toString(), is("0"));
    }

    @Test
    public void testOnesValues() {
        Duration durationOfOnes = Duration.ofNanos(1)
                .plusMillis(1).plusSeconds(1).plusMinutes(1).plusHours(1)
                .plusDays(1);

        ITimeInterval timeIntervalNonVerbose = new TimeInterval(durationOfOnes, false);
        ITimeInterval timeIntervalVerbose = new TimeInterval(durationOfOnes, true);

        assertThat(timeIntervalNonVerbose.toString(), is("1d:1h:1m:1s:1ms:1ns"));
        assertThat(timeIntervalVerbose.toString(), is("1day:1hour:1minute:1second:1millisecond:1nanosecond"));
    }

    @Test
    public void testPartialValues() {
        Map<Duration, String> expectedResults = new HashMap<>();
        Map<Duration, String> expectedVerboseResults = new HashMap<>();

        Duration duration = Duration.ofNanos(NANOS);
        expectedResults.put(duration, NANOS + "ns");
        expectedVerboseResults.put(duration, NANOS + "nanoseconds");

        duration = duration.plusMinutes(MINUTES);
        expectedResults.put(duration, MINUTES + "m" + ":" + NANOS + "ns");
        expectedVerboseResults.put(duration, MINUTES + "minutes" + ":" + NANOS + "nanoseconds");

        duration = duration.plusHours(HOURS);
        expectedResults.put(duration, HOURS + "h" + ":" + MINUTES + "m" + ":" + NANOS + "ns");
        expectedVerboseResults.put(duration, HOURS + "hours" + ":" + MINUTES + "minutes" + ":" + NANOS + "nanoseconds");

        for (Map.Entry<Duration, String> expectedResult : expectedResults.entrySet()) {
            ITimeInterval timeIntervalVerbose = new TimeInterval(expectedResult.getKey(), false);
            assertThat(timeIntervalVerbose.toString(), is(expectedResult.getValue()));
        }

        for (Map.Entry<Duration, String> expectedResult : expectedVerboseResults.entrySet()) {
            ITimeInterval timeIntervalVerbose = new TimeInterval(expectedResult.getKey(), true);
            assertThat(timeIntervalVerbose.toString(), is(expectedResult.getValue()));
        }
    }

}