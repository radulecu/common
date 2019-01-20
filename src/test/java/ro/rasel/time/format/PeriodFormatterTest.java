package ro.rasel.time.format;

import org.junit.Test;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PeriodFormatterTest {

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
        ITemporalAmountFormatter<Period, ChronoUnit> periodFormatter = new PeriodFormatter(DURATION);
        assertThat(periodFormatter.getAsInt(ChronoUnit.YEARS), is(YEARS));
        assertThat(periodFormatter.getAsInt(ChronoUnit.MONTHS), is(MONTHS));
        assertThat(periodFormatter.getAsInt(ChronoUnit.DAYS), is(DAYS));
    }

    @Test
    public void testToString() {
        ITemporalAmountFormatter<Period, ChronoUnit> periodFormatterNonVerbose = new PeriodFormatter(DURATION, false);
        ITemporalAmountFormatter<Period, ChronoUnit> periodFormatterVerbose = new PeriodFormatter(DURATION, true);

        assertThat(periodFormatterNonVerbose.toString(), is(DURATION_AS_STRING));
        assertThat(periodFormatterVerbose.toString(), is(DURATION_AS_STRING_VERBOSE));

        assertThat(PeriodFormatter.of(DURATION_AS_STRING).getTime(), is(DURATION));
        assertThat(PeriodFormatter.of(DURATION_AS_STRING_VERBOSE, true).getTime(), is(DURATION));
    }

    @Test
    public void testZeroValues() {
        final Period periodOfZeros = Period.ofDays(0);
        final String periodOfZerosAsString = "0";

        ITemporalAmountFormatter<Period, ChronoUnit> periodFormatterNonVerbose =
                new PeriodFormatter(periodOfZeros, false);
        ITemporalAmountFormatter<Period, ChronoUnit> periodFormatterVerbose = new PeriodFormatter(periodOfZeros, true);

        assertThat(periodFormatterNonVerbose.toString(), is(periodOfZerosAsString));
        assertThat(periodFormatterVerbose.toString(), is(periodOfZerosAsString));

        assertThat(PeriodFormatter.of(periodOfZerosAsString).getTime(), is(periodOfZeros));
        assertThat(PeriodFormatter.of(periodOfZerosAsString, true).getTime(), is(periodOfZeros));

    }

    @Test
    public void testOnesValues() {
        Period periodOfOnes = Period.ofDays(1).plusMonths(1).plusYears(1);
        final String periodOfOnesAsString = "1y:1mo:1d";
        final String periodOfOnesAsStringVerbose = "1 year:1 month:1 day";

        ITemporalAmountFormatter<Period, ChronoUnit> periodFormatterNonVerbose =
                new PeriodFormatter(periodOfOnes, false);
        ITemporalAmountFormatter<Period, ChronoUnit> periodFormatterVerbose = new PeriodFormatter(periodOfOnes, true);

        assertThat(periodFormatterNonVerbose.toString(), is(periodOfOnesAsString));
        assertThat(periodFormatterVerbose.toString(), is(periodOfOnesAsStringVerbose));

        assertThat(PeriodFormatter.of(periodOfOnesAsString).getTime(), is(periodOfOnes));
        assertThat(PeriodFormatter.of(periodOfOnesAsStringVerbose, true).getTime(), is(periodOfOnes));
    }

    @Test
    public void testPartialValues() {
        Map<Period, String> expectedResults = new HashMap<>();
        Map<Period, String> expectedVerboseResults = new HashMap<>();

        Period period = Period.ofMonths(MONTHS);
        String periodAsString = MONTHS + "mo";
        String periodAsStringVerbose = MONTHS + " months";
        assertThat(new PeriodFormatter(period, false).toString(), is(periodAsString));
        assertThat(new PeriodFormatter(period, true).toString(), is(periodAsStringVerbose));
        assertThat(PeriodFormatter.of(periodAsString).getTime(), is(period));
        assertThat(PeriodFormatter.of(periodAsStringVerbose, true).getTime(), is(period));

        period = period.plusDays(DAYS);
        periodAsString = MONTHS + "mo" + ":" + DAYS + "d";
        periodAsStringVerbose = MONTHS + " months" + ":" + DAYS + " days";
        assertThat(new PeriodFormatter(period, false).toString(), is(periodAsString));
        assertThat(new PeriodFormatter(period, true).toString(), is(periodAsStringVerbose));
        assertThat(PeriodFormatter.of(periodAsString).getTime(), is(period));
        assertThat(PeriodFormatter.of(periodAsStringVerbose, true).getTime(), is(period));

        period = period.plusYears(YEARS);
        periodAsString = YEARS + "y" + ":" + MONTHS + "mo" + ":" + DAYS + "d";
        periodAsStringVerbose = YEARS + " years" + ":" + MONTHS + " months" + ":" + DAYS + " days";
        assertThat(new PeriodFormatter(period, false).toString(), is(periodAsString));
        assertThat(new PeriodFormatter(period, true).toString(), is(periodAsStringVerbose));
        assertThat(PeriodFormatter.of(periodAsString).getTime(), is(period));
        assertThat(PeriodFormatter.of(periodAsStringVerbose, true).getTime(), is(period));
    }

    @Test
    public void testPeriodNormalised() {
        Period period = Period.of(YEARS, MONTHS + 24, DAYS);
        ITemporalAmountFormatter<Period, ChronoUnit> periodFormatter = new PeriodFormatter(period);
        ITemporalAmountFormatter<Period, ChronoUnit> periodFormatterNormalised =
                new PeriodFormatter(period.normalized());

        assertThat(periodFormatter.getAsInt(ChronoUnit.YEARS), is(YEARS));
        assertThat(periodFormatter.getAsInt(ChronoUnit.MONTHS), is(MONTHS + 24));
        assertThat(periodFormatter.getAsInt(ChronoUnit.DAYS), is(DAYS));

        assertThat(PeriodFormatter.of(periodFormatter.toString()).getTime(), is(period));

        assertThat(periodFormatterNormalised.getAsInt(ChronoUnit.YEARS), is(YEARS + 2));
        assertThat(periodFormatterNormalised.getAsInt(ChronoUnit.MONTHS), is(MONTHS));
        assertThat(periodFormatterNormalised.getAsInt(ChronoUnit.DAYS), is(DAYS));

        assertThat(PeriodFormatter.of(periodFormatterNormalised.toString()).getTime(), is(period.normalized()));

    }
}