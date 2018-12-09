package ro.rasel.time;

import java.time.Duration;
import java.util.Objects;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeIntervalImpl implements TimeInterval {
    private Duration duration;
    private final boolean verbose;

    public TimeIntervalImpl(Duration duration) {
        this(duration, false);
    }

    public TimeIntervalImpl(Duration duration, boolean verbose) {
        this.duration = duration;
        this.verbose = verbose;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public int getNanoseconds() {
        return (int) (duration.toNanos() % 1_000_000);
    }

    @Override
    public int getMilliseconds() {
        return (int) (duration.toMillis() % 1000);
    }

    @Override
    public int getSeconds() {
        return (int) (duration.getSeconds() % 60);
    }

    @Override
    public int getMinutes() {
        return (int) (duration.toMinutes() % 60);
    }

    @Override
    public int getHours() {
        return (int) (duration.toHours() % 24);
    }

    @Override
    public long getDays() {
        return duration.toDays();
    }

    @Override
    public String toString() {
        boolean verbose = this.verbose;
        return toString(verbose);
    }

    public String toString(boolean verbose) {
        return format(verbose ? TimeFormatterImpl.VERBOSE_FORMATTER : TimeFormatterImpl.SIMPLE_FORMATTER);
    }

    public String format(TimeFormatter timeFormatter) {
        String s = Stream.of(new String[]{format(getDays(), timeFormatter.getDaysFormatter()),
                format(getHours(), timeFormatter.getHoursFormatter()),
                format(getMinutes(), timeFormatter.getMinutesFormatter()),
                format(getSeconds(), timeFormatter.getSecondsFormatter()),
                format(getMilliseconds(), timeFormatter.getMillisecondsFormatter()),
                format(getNanoseconds(), timeFormatter.getNanosecondsFormatter())}).
                filter(Objects::nonNull).collect(Collectors.joining(timeFormatter.getSeparator()));
        return s.isEmpty()?"0":s;
    }

    private static String format(long value, LongFunction<String> function) {
        if (value == 0) {
            return null;
        } else {
            return function.apply(value);
        }
    }
}
