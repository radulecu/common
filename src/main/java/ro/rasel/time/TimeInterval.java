package ro.rasel.time;

import ro.rasel.collections.MapBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class TimeInterval implements ITimeInterval<Duration, TimeUnit> {
    private static final Map<TimeUnit, ToLongFunction<Duration>> TIME_PROVIDER =
            MapBuilder.<TimeUnit, ToLongFunction<Duration>>ofMap()
                    .put(DAYS, Duration::toDays)
                    .put(HOURS, duration -> duration.toHours() % 24)
                    .put(MINUTES, duration -> duration.toMinutes() % 60)
                    .put(SECONDS, duration -> duration.getSeconds() % 60)
                    .put(MILLISECONDS, duration -> duration.toMillis() % 1000)
                    .put(NANOSECONDS, duration -> duration.toNanos() % 1_000_000)
                    .build(HashMap::new);
    private Duration duration;
    private final boolean verbose;

    public TimeInterval(Duration duration) {
        this(duration, false);
    }

    public TimeInterval(Duration duration, boolean verbose) {
        this.duration = duration;
        this.verbose = verbose;
    }

    @Override
    public Duration getTime() {
        return duration;
    }

    public long get(TimeUnit timeUnit) {
        return TIME_PROVIDER.get(timeUnit).applyAsLong(getTime());
    }

    @Override
    public String toString() {
        return toString(this.verbose);
    }

    public String toString(boolean verbose) {
        return format(verbose ? TimeFormatter.VERBOSE_FORMATTER : TimeFormatter.SIMPLE_FORMATTER);
    }

    public String format(ITimeFormatter<TimeUnit> timeFormatter) {
        String s = Stream.of(new String[]{
                format(get(DAYS), timeFormatter.getFormatter(DAYS)),
                format(get(HOURS), timeFormatter.getFormatter(HOURS)),
                format(get(MINUTES), timeFormatter.getFormatter(MINUTES)),
                format(get(SECONDS), timeFormatter.getFormatter(SECONDS)),
                format(get(MILLISECONDS), timeFormatter.getFormatter(MILLISECONDS)),
                format(get(NANOSECONDS), timeFormatter.getFormatter(NANOSECONDS))}).
                filter(Objects::nonNull).collect(Collectors.joining(timeFormatter.getSeparator()));
        return s.isEmpty() ? "0" : s;
    }

    private static String format(long value, LongFunction<String> function) {
        if (value == 0) {
            return null;
        } else {
            return function.apply(value);
        }
    }
}
