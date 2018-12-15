package ro.rasel.time;

import org.junit.Test;

import java.time.Period;
import java.time.Year;
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
        ITimeInterval<Period, ChronoUnit> timeInterval = new PeriodInterval(DURATION);
        assertThat(timeInterval.getAsInt(ChronoUnit.YEARS), is(YEARS));
        assertThat(timeInterval.getAsInt(ChronoUnit.MONTHS), is(MONTHS));
        assertThat(timeInterval.getAsInt(ChronoUnit.DAYS), is(DAYS));
    }

    @Test
    public void testToString() {
        ITimeInterval<Period, ChronoUnit> timeIntervalNonVerbose = new PeriodInterval(DURATION, false);
        ITimeInterval<Period, ChronoUnit> timeIntervalVerbose = new PeriodInterval(DURATION, true);

        assertThat(timeIntervalNonVerbose.toString(), is(DURATION_AS_STRING));
        assertThat(timeIntervalVerbose.toString(), is(DURATION_AS_STRING_VERBOSE));
    }

    @Test
    public void testZeroValues() {
        ITimeInterval<Period, ChronoUnit> timeIntervalNonVerbose = new PeriodInterval(Period.ofDays(0), false);
        ITimeInterval<Period, ChronoUnit> timeIntervalVerbose = new PeriodInterval(Period.ofDays(0), true);

        assertThat(timeIntervalNonVerbose.toString(), is("0"));
        assertThat(timeIntervalVerbose.toString(), is("0"));
    }

    @Test
    public void testOnesValues() {
        Period periodOfOnes = Period.ofDays(1)
                .plusMonths(1).plusYears(1);

        ITimeInterval<Period, ChronoUnit> timeIntervalNonVerbose = new PeriodInterval(periodOfOnes, false);
        ITimeInterval<Period, ChronoUnit> timeIntervalVerbose = new PeriodInterval(periodOfOnes, true);

        assertThat(timeIntervalNonVerbose.toString(), is("1y:1mo:1d"));
        assertThat(timeIntervalVerbose.toString(), is("1 year:1 month:1 day"));
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
            ITimeInterval<Period, ChronoUnit> timeIntervalVerbose = new PeriodInterval(expectedResult.getKey(), false);
            assertThat(timeIntervalVerbose.toString(), is(expectedResult.getValue()));
        }

        for (Map.Entry<Period, String> expectedResult : expectedVerboseResults.entrySet()) {
            ITimeInterval<Period, ChronoUnit> timeIntervalVerbose = new PeriodInterval(expectedResult.getKey(), true);
            assertThat(timeIntervalVerbose.toString(), is(expectedResult.getValue()));
        }
    }

    @Test
    public void testPeriodNormalised(){
        Period period = Period.of(YEARS, MONTHS + 24, DAYS);
        ITimeInterval<Period,ChronoUnit> timeInterval=new PeriodInterval(period);
        ITimeInterval<Period,ChronoUnit> timeIntervalNormalised=new PeriodInterval(period.normalized());

        assertThat(timeInterval.getAsInt(ChronoUnit.YEARS), is(YEARS));
        assertThat(timeInterval.getAsInt(ChronoUnit.MONTHS), is(MONTHS+24));
        assertThat(timeInterval.getAsInt(ChronoUnit.DAYS), is(DAYS));

        assertThat(timeIntervalNormalised.getAsInt(ChronoUnit.YEARS), is(YEARS+2));
        assertThat(timeIntervalNormalised.getAsInt(ChronoUnit.MONTHS), is(MONTHS));
        assertThat(timeIntervalNormalised.getAsInt(ChronoUnit.DAYS), is(DAYS));


    }
}