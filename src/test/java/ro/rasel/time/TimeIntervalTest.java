package ro.rasel.time;

import org.junit.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TimeIntervalTest {

    private static final long NANOSECONDS = 345;
    private static final long MICROSECONDS = 12;
    private static final long MILLISECONDS = 25;
    private static final long SECONDS = 5;
    private static final long MINUTES = 7;
    private static final long HOURS = 9;
    private static final long DAYS = 3;

    private static final Duration DURATION =
            Duration.ofNanos(NANOSECONDS + MICROSECONDS * 1000).plusMillis(MILLISECONDS).plusSeconds(SECONDS)
                    .plusMinutes(MINUTES).plusHours(HOURS).plusDays(DAYS);
    private static final String DURATION_AS_STRING =
            String.format("%dd:%sh:%sm:%ss:%sms:%sus:%sns", DAYS, HOURS, MINUTES, SECONDS, MILLISECONDS, MICROSECONDS,
                    NANOSECONDS);
    private static final String DURATION_AS_STRING_VERBOSE =
            String.format("%d days:%s hours:%s minutes:%s seconds:%s milliseconds:%s microseconds:%s nanoseconds", DAYS,
                    HOURS, MINUTES, SECONDS, MILLISECONDS, MICROSECONDS, NANOSECONDS);

    @Test
    public void testGetters() {
        ITimeInterval<Duration, TimeUnit> timeInterval = new TimeInterval(DURATION);
        assertThat(timeInterval.get(TimeUnit.NANOSECONDS), is(NANOSECONDS));
        assertThat(timeInterval.get(TimeUnit.MICROSECONDS), is(MICROSECONDS));
        assertThat(timeInterval.get(TimeUnit.MILLISECONDS), is(MILLISECONDS));
        assertThat(timeInterval.get(TimeUnit.DAYS), is(DAYS));
        assertThat(timeInterval.get(TimeUnit.MINUTES), is(MINUTES));
        assertThat(timeInterval.get(TimeUnit.HOURS), is(HOURS));
        assertThat(timeInterval.get(TimeUnit.SECONDS), is(SECONDS));
    }

    @Test
    public void testToString() {
        ITimeInterval<Duration, TimeUnit> timeIntervalNonVerbose = new TimeInterval(DURATION, false);
        ITimeInterval<Duration, TimeUnit> timeIntervalVerbose = new TimeInterval(DURATION, true);

        assertThat(timeIntervalNonVerbose.toString(), is(DURATION_AS_STRING));
        assertThat(timeIntervalVerbose.toString(), is(DURATION_AS_STRING_VERBOSE));
    }

    @Test
    public void testZeroValues() {
        ITimeInterval<Duration, TimeUnit> timeIntervalNonVerbose = new TimeInterval(Duration.ofSeconds(0), false);
        ITimeInterval<Duration, TimeUnit> timeIntervalVerbose = new TimeInterval(Duration.ofSeconds(0), true);

        assertThat(timeIntervalNonVerbose.toString(), is("0"));
        assertThat(timeIntervalVerbose.toString(), is("0"));
    }

    @Test
    public void testOnesValues() {
        Duration durationOfOnes = Duration.ofNanos(1)
                .plusMillis(1).plusSeconds(1).plusMinutes(1).plusHours(1)
                .plusDays(1);

        ITimeInterval<Duration, TimeUnit> timeIntervalNonVerbose = new TimeInterval(durationOfOnes, false);
        ITimeInterval<Duration, TimeUnit> timeIntervalVerbose = new TimeInterval(durationOfOnes, true);

        assertThat(timeIntervalNonVerbose.toString(), is("1d:1h:1m:1s:1ms:1ns"));
        assertThat(timeIntervalVerbose.toString(), is("1 day:1 hour:1 minute:1 second:1 millisecond:1 nanosecond"));
    }

    @Test
    public void testPartialValues() {
        Map<Duration, String> expectedResults = new HashMap<>();
        Map<Duration, String> expectedVerboseResults = new HashMap<>();

        Duration duration = Duration.ofNanos(NANOSECONDS);
        expectedResults.put(duration, NANOSECONDS + "ns");
        expectedVerboseResults.put(duration, NANOSECONDS + " nanoseconds");

        duration = duration.plusMinutes(MINUTES);
        expectedResults.put(duration, MINUTES + "m" + ":" + NANOSECONDS + "ns");
        expectedVerboseResults.put(duration, MINUTES + " minutes" + ":" + NANOSECONDS + " nanoseconds");

        duration = duration.plusHours(HOURS);
        expectedResults.put(duration, HOURS + "h" + ":" + MINUTES + "m" + ":" + NANOSECONDS + "ns");
        expectedVerboseResults
                .put(duration, HOURS + " hours" + ":" + MINUTES + " minutes" + ":" + NANOSECONDS + " nanoseconds");

        for (Map.Entry<Duration, String> expectedResult : expectedResults.entrySet()) {
            ITimeInterval<Duration, TimeUnit> timeIntervalVerbose = new TimeInterval(expectedResult.getKey(), false);
            assertThat(timeIntervalVerbose.toString(), is(expectedResult.getValue()));
        }

        for (Map.Entry<Duration, String> expectedResult : expectedVerboseResults.entrySet()) {
            ITimeInterval<Duration, TimeUnit> timeIntervalVerbose = new TimeInterval(expectedResult.getKey(), true);
            assertThat(timeIntervalVerbose.toString(), is(expectedResult.getValue()));
        }
    }

}