package ro.rasel.time;

import org.junit.Test;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PeriodIntervalTest {

    private static final int DAYS = 18;
    private static final int MONTHS = 6;
    private static final int YEARS = 3;

    private static final Period DURATION =
            Period.ofDays(DAYS).plusMonths(MONTHS).plusYears(YEARS);
    private static final String DURATION_AS_STRING =
            String.format("%dy:%smo:%sd", YEARS, MONTHS, DAYS);
    private static final String DURATION_AS_STRING_VERBOSE =
            String.format("%d years:%s months:%s days", YEARS, MONTHS, DAYS);

    @Test
    public void testGetters() {
        ITimeFormatter<Period, ChronoUnit> timeFormatter = new PeriodInterval(DURATION);
        assertThat(timeFormatter.getAsInt(ChronoUnit.YEARS), is(YEARS));
        assertThat(timeFormatter.getAsInt(ChronoUnit.MONTHS), is(MONTHS));
        assertThat(timeFormatter.getAsInt(ChronoUnit.DAYS), is(DAYS));
    }

    @Test
    public void testToString() {
        ITimeFormatter<Period, ChronoUnit> timeFormatterNonVerbose = new PeriodInterval(DURATION, false);
        ITimeFormatter<Period, ChronoUnit> timeFormatterVerbose = new PeriodInterval(DURATION, true);

        assertThat(timeFormatterNonVerbose.toString(), is(DURATION_AS_STRING));
        assertThat(timeFormatterVerbose.toString(), is(DURATION_AS_STRING_VERBOSE));
    }

    @Test
    public void testZeroValues() {
        ITimeFormatter<Period, ChronoUnit> timeFormatterNonVerbose = new PeriodInterval(Period.ofDays(0), false);
        ITimeFormatter<Period, ChronoUnit> timeFormatterVerbose = new PeriodInterval(Period.ofDays(0), true);

        assertThat(timeFormatterNonVerbose.toString(), is("0"));
        assertThat(timeFormatterVerbose.toString(), is("0"));
    }

    @Test
    public void testOnesValues() {
        Period periodOfOnes = Period.ofDays(1)
                .plusMonths(1).plusYears(1);

        ITimeFormatter<Period, ChronoUnit> timeFormatterNonVerbose = new PeriodInterval(periodOfOnes, false);
        ITimeFormatter<Period, ChronoUnit> timeFormatterVerbose = new PeriodInterval(periodOfOnes, true);

        assertThat(timeFormatterNonVerbose.toString(), is("1y:1mo:1d"));
        assertThat(timeFormatterVerbose.toString(), is("1 year:1 month:1 day"));
    }

    @Test
    public void testPartialValues() {
        Map<Period, String> expectedResults = new HashMap<>();
        Map<Period, String> expectedVerboseResults = new HashMap<>();

        Period duration = Period.ofMonths(MONTHS);
        expectedResults.put(duration, MONTHS + "mo");
        expectedVerboseResults.put(duration, MONTHS + " months");

        duration = duration.plusDays(DAYS);
        expectedResults.put(duration, MONTHS + "mo" + ":" + DAYS + "d");
        expectedVerboseResults.put(duration, MONTHS + " months" + ":" + DAYS + " days");

        duration = duration.plusYears(YEARS);
        expectedResults.put(duration, YEARS + "y" + ":" + MONTHS + "mo" + ":" + DAYS + "d");
        expectedVerboseResults
                .put(duration, YEARS + " years" + ":" + MONTHS + " months" + ":" + DAYS + " days");

        for (Map.Entry<Period, String> expectedResult : expectedResults.entrySet()) {
            ITimeFormatter<Period, ChronoUnit> timeFormatterVerbose =
                    new PeriodInterval(expectedResult.getKey(), false);
            assertThat(timeFormatterVerbose.toString(), is(expectedResult.getValue()));
        }

        for (Map.Entry<Period, String> expectedResult : expectedVerboseResults.entrySet()) {
            ITimeFormatter<Period, ChronoUnit> timeFormatterVerbose = new PeriodInterval(expectedResult.getKey(), true);
            assertThat(timeFormatterVerbose.toString(), is(expectedResult.getValue()));
        }
    }

    @Test
    public void testPeriodNormalised() {
        Period period = Period.of(YEARS, MONTHS + 24, DAYS);
        ITimeFormatter<Period, ChronoUnit> timeFormatter = new PeriodInterval(period);
        ITimeFormatter<Period, ChronoUnit> timeFormatterNormalised = new PeriodInterval(period.normalized());

        assertThat(timeFormatter.getAsInt(ChronoUnit.YEARS), is(YEARS));
        assertThat(timeFormatter.getAsInt(ChronoUnit.MONTHS), is(MONTHS + 24));
        assertThat(timeFormatter.getAsInt(ChronoUnit.DAYS), is(DAYS));

        assertThat(timeFormatterNormalised.getAsInt(ChronoUnit.YEARS), is(YEARS + 2));
        assertThat(timeFormatterNormalised.getAsInt(ChronoUnit.MONTHS), is(MONTHS));
        assertThat(timeFormatterNormalised.getAsInt(ChronoUnit.DAYS), is(DAYS));

    }
}