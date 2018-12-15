package ro.rasel.time;

import ro.rasel.collections.MapBuilder;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.ToLongFunction;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MICROS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;

public class TimeInterval extends AbstractTimeInterval<Duration, ChronoUnit> {
    private static final Map<ChronoUnit, ToLongFunction<Duration>> TIME_PROVIDER =
            MapBuilder.<ChronoUnit, ToLongFunction<Duration>>ofOrderedMap()
                    .put(DAYS, Duration::toDays)
                    .put(HOURS, duration -> duration.toHours() % 24)
                    .put(MINUTES, duration -> duration.toMinutes() % 60)
                    .put(SECONDS, duration -> duration.getSeconds() % 60)
                    .put(MILLIS, duration -> duration.toMillis() % 1000)
                    .put(MICROS, duration -> duration.toNanos() / 1000 % 1000)
                    .put(NANOS, duration -> duration.toNanos() % 1000)
                    .build();
    private final boolean verbose;

    public TimeInterval(Duration duration) {
        this(duration, false);
    }

    public TimeInterval(Duration duration, boolean verbose) {
        super(duration, TIME_PROVIDER);
        this.verbose = verbose;
    }

    @Override
    public String toString() {
        return toString(this.verbose);
    }

    public String toString(boolean verbose) {
        return format(verbose ? TimeFormatter.VERBOSE_FORMATTER : TimeFormatter.SIMPLE_FORMATTER);
    }
}
