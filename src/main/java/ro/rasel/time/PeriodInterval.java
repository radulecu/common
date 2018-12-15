package ro.rasel.time;

import ro.rasel.collections.MapBuilder;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.ToLongFunction;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

public class PeriodInterval extends AbstractTimeFormatter<Period, ChronoUnit> {
    private static final Map<ChronoUnit, ToLongFunction<Period>> TIME_PROVIDER =
            MapBuilder.<ChronoUnit, ToLongFunction<Period>>ofOrderedMap()
                    .put(YEARS, Period::getYears)
                    .put(MONTHS, Period::getMonths)
                    .put(DAYS, Period::getDays)
                    .build();
    private final boolean verbose;

    public PeriodInterval(Period period) {
        this(period, false);
    }

    public PeriodInterval(Period period, boolean verbose) {
        super(period, TIME_PROVIDER);
        this.verbose = verbose;
    }

    @Override
    public String toString() {
        return toString(this.verbose);
    }

    public String toString(boolean verbose) {
        return format(verbose ? TimeUnitFormatter.VERBOSE_FORMATTER : TimeUnitFormatter.SIMPLE_FORMATTER);
    }
}
