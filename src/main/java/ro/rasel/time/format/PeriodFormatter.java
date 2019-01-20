package ro.rasel.time.format;

import ro.rasel.collections.MapBuilder;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToLongFunction;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

public class PeriodFormatter extends AbstractChronoUnitTemporalAmountFormatter<Period> implements IPeriodFormatter {
    private static final Map<ChronoUnit, ToLongFunction<Period>> TEMPORAL_AMOUNT_TO_CHRONO_UNIT_CONVERTER =
            MapBuilder.<ChronoUnit, ToLongFunction<Period>>ofOrderedMap()
                    .put(YEARS, Period::getYears)
                    .put(MONTHS, Period::getMonths)
                    .put(DAYS, Period::getDays)
                    .build();
    private static final Function<Map<ChronoUnit, Long>, Period> CHRONO_UNIT_MAP_TO_TEMPORAL_AMOUNT_CONVERTER =
            map -> Period.of(map.getOrDefault(YEARS, 0L).intValue(), map.getOrDefault(MONTHS, 0L).intValue(),
                    map.getOrDefault(DAYS, 0L).intValue());
    private final boolean verbose;

    public PeriodFormatter(Period period) {
        this(period, false);
    }

    public PeriodFormatter(Period period, boolean verbose) {
        super(period, TEMPORAL_AMOUNT_TO_CHRONO_UNIT_CONVERTER);
        this.verbose = verbose;
    }

    public static PeriodFormatter of(String s) {
        return of(s, false);
    }

    public static PeriodFormatter of(String s, boolean verbose) {
        return new PeriodFormatter(parse(s, TEMPORAL_AMOUNT_TO_CHRONO_UNIT_CONVERTER.keySet(),
                CHRONO_UNIT_MAP_TO_TEMPORAL_AMOUNT_CONVERTER, verbose),
                verbose);
    }

    public static PeriodFormatter of(String s,
            Function<Map<ChronoUnit, Long>, Period> chronoUnitMapToTemporalAmountConverter,
            ITemporalUnitFormatter<ChronoUnit> temporalUnitFormatter) {
        return of(s, chronoUnitMapToTemporalAmountConverter, temporalUnitFormatter, false);
    }

    public static PeriodFormatter of(String s,
            Function<Map<ChronoUnit, Long>, Period> chronoUnitMapToTemporalAmountConverter,
            ITemporalUnitFormatter<ChronoUnit> temporalUnitFormatter, boolean verbose) {
        return new PeriodFormatter(
                parse(s, TEMPORAL_AMOUNT_TO_CHRONO_UNIT_CONVERTER.keySet(), chronoUnitMapToTemporalAmountConverter,
                        temporalUnitFormatter), verbose);
    }

    @Override
    public String toString() {
        return toString(this.verbose);
    }

}
