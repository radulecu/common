package ro.rasel.time;

import org.junit.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TimeIntervalTest {

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
        ITimeInterval<Duration, ChronoUnit> timeInterval = new TimeInterval(DURATION);
        assertThat(timeInterval.getAsInt(ChronoUnit.NANOS), is(NANOS));
        assertThat(timeInterval.getAsInt(ChronoUnit.MICROS), is(MICROS));
        assertThat(timeInterval.getAsInt(ChronoUnit.MILLIS), is(MILLIS));
        assertThat(timeInterval.getAsInt(ChronoUnit.DAYS), is(DAYS));
        assertThat(timeInterval.getAsInt(ChronoUnit.MINUTES), is(MINUTES));
        assertThat(timeInterval.getAsInt(ChronoUnit.HOURS), is(HOURS));
        assertThat(timeInterval.getAsInt(ChronoUnit.SECONDS), is(SECONDS));
    }

    @Test
    public void testToString() {
        ITimeInterval<Duration, ChronoUnit> timeIntervalNonVerbose = new TimeInterval(DURATION, false);
        ITimeInterval<Duration, ChronoUnit> timeIntervalVerbose = new TimeInterval(DURATION, true);

        assertThat(timeIntervalNonVerbose.toString(), is(DURATION_AS_STRING));
        assertThat(timeIntervalVerbose.toString(), is(DURATION_AS_STRING_VERBOSE));
    }

    @Test
    public void testZeroValues() {
        ITimeInterval<Duration, ChronoUnit> timeIntervalNonVerbose = new TimeInterval(Duration.ofSeconds(0), false);
        ITimeInterval<Duration, ChronoUnit> timeIntervalVerbose = new TimeInterval(Duration.ofSeconds(0), true);

        assertThat(timeIntervalNonVerbose.toString(), is("0"));
        assertThat(timeIntervalVerbose.toString(), is("0"));
    }

    @Test
    public void testOnesValues() {
        Duration durationOfOnes = Duration.ofNanos(1)
                .plusMillis(1).plusSeconds(1).plusMinutes(1).plusHours(1)
                .plusDays(1);

        ITimeInterval<Duration, ChronoUnit> timeIntervalNonVerbose = new TimeInterval(durationOfOnes, false);
        ITimeInterval<Duration, ChronoUnit> timeIntervalVerbose = new TimeInterval(durationOfOnes, true);

        assertThat(timeIntervalNonVerbose.toString(), is("1d:1h:1m:1s:1ms:1ns"));
        assertThat(timeIntervalVerbose.toString(), is("1 day:1 hour:1 minute:1 second:1 millisecond:1 nanosecond"));
    }

    @Test
    public void testPartialValues() {
        Map<Duration, String> expectedResults = new HashMap<>();
        Map<Duration, String> expectedVerboseResults = new HashMap<>();

        Duration duration = Duration.ofNanos(NANOS);
        expectedResults.put(duration, NANOS + "ns");
        expectedVerboseResults.put(duration, NANOS + " nanoseconds");

        duration = duration.plusMinutes(MINUTES);
        expectedResults.put(duration, MINUTES + "m" + ":" + NANOS + "ns");
        expectedVerboseResults.put(duration, MINUTES + " minutes" + ":" + NANOS + " nanoseconds");

        duration = duration.plusHours(HOURS);
        expectedResults.put(duration, HOURS + "h" + ":" + MINUTES + "m" + ":" + NANOS + "ns");
        expectedVerboseResults
                .put(duration, HOURS + " hours" + ":" + MINUTES + " minutes" + ":" + NANOS + " nanoseconds");

        for (Map.Entry<Duration, String> expectedResult : expectedResults.entrySet()) {
            ITimeInterval<Duration, ChronoUnit> timeIntervalVerbose = new TimeInterval(expectedResult.getKey(), false);
            assertThat(timeIntervalVerbose.toString(), is(expectedResult.getValue()));
        }

        for (Map.Entry<Duration, String> expectedResult : expectedVerboseResults.entrySet()) {
            ITimeInterval<Duration, ChronoUnit> timeIntervalVerbose = new TimeInterval(expectedResult.getKey(), true);
            assertThat(timeIntervalVerbose.toString(), is(expectedResult.getValue()));
        }
    }

    @Test(expected = RuntimeException.class)
    public void testDaysValueToBig() {
        ITimeInterval<Duration,ChronoUnit> timeInterval=new TimeInterval(Duration.ofDays(Integer.MAX_VALUE+1L));
        timeInterval.getAsInt(ChronoUnit.DAYS);
    }

}