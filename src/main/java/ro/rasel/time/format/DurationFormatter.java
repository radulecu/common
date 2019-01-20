package ro.rasel.time.format;

import ro.rasel.collections.MapBuilder;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToLongFunction;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MICROS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;

public class DurationFormatter extends AbstractChronoUnitTemporalAmountFormatter<Duration>
        implements IDurationFormatter {
    private static final Map<ChronoUnit, ToLongFunction<Duration>> TEMPORAL_AMOUNT_TO_CHRONO_UNIT_CONVERTER =
            MapBuilder.<ChronoUnit, ToLongFunction<Duration>>ofOrderedMap()
                    .put(DAYS, Duration::toDays)
                    .put(HOURS, duration -> duration.toHours() % 24)
                    .put(MINUTES, duration -> duration.toMinutes() % 60)
                    .put(SECONDS, duration -> duration.getSeconds() % 60)
                    .put(MILLIS, duration -> duration.toMillis() % 1000)
                    .put(MICROS, duration -> duration.toNanos() / 1000 % 1000)
                    .put(NANOS, duration -> duration.toNanos() % 1000)
                    .build();
    private static final Function<Map<ChronoUnit, Long>, Duration> CHRONO_UNIT_MAP_TO_TEMPORAL_AMOUNT_CONVERTER =
            map -> Duration
                    .ofDays(map.getOrDefault(DAYS, 0L))
                    .plusHours(map.getOrDefault(HOURS, 0L))
                    .plusMinutes(map.getOrDefault(MINUTES, 0L))
                    .plusSeconds(map.getOrDefault(SECONDS, 0L))
                    .plusMillis(map.getOrDefault(MILLIS, 0L))
                    .plusNanos(map.getOrDefault(MICROS, 0L) * 1000 + map.getOrDefault(NANOS, 0L));
    private final boolean verbose;

    public DurationFormatter(Duration duration) {
        this(duration, false);
    }

    public DurationFormatter(Duration duration, boolean verbose) {
        super(duration, TEMPORAL_AMOUNT_TO_CHRONO_UNIT_CONVERTER);
        this.verbose = verbose;
    }

    public static DurationFormatter of(String s) {
        return of(s, false);
    }

    public static DurationFormatter of(String s, boolean verbose) {
        return new DurationFormatter(parse(s, TEMPORAL_AMOUNT_TO_CHRONO_UNIT_CONVERTER.keySet(),
                CHRONO_UNIT_MAP_TO_TEMPORAL_AMOUNT_CONVERTER, verbose));
    }

    public static DurationFormatter of(String s,
            Function<Map<ChronoUnit, Long>, Duration> chronoUnitMapToTemporalAmountConverter,
            ITemporalUnitFormatter<ChronoUnit> temporalUnitFormatter) {
        return of(s, chronoUnitMapToTemporalAmountConverter, temporalUnitFormatter, false);
    }

    public static DurationFormatter of(String s,
            Function<Map<ChronoUnit, Long>, Duration> chronoUnitMapToTemporalAmountConverter,
            ITemporalUnitFormatter<ChronoUnit> temporalUnitFormatter, boolean verbose) {
        return new DurationFormatter(
                parse(s, TEMPORAL_AMOUNT_TO_CHRONO_UNIT_CONVERTER.keySet(), chronoUnitMapToTemporalAmountConverter,
                        temporalUnitFormatter), verbose);
    }

    @Override
    public String toString() {
        return toString(this.verbose);
    }
}
