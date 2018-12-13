package ro.rasel.time;

import java.time.Duration;
import java.util.Objects;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.rasel.time.TimeFormatter.FormatterUnit.Days;
import static ro.rasel.time.TimeFormatter.FormatterUnit.Hours;
import static ro.rasel.time.TimeFormatter.FormatterUnit.Milliseconds;
import static ro.rasel.time.TimeFormatter.FormatterUnit.Minutes;
import static ro.rasel.time.TimeFormatter.FormatterUnit.Nanoseconds;
import static ro.rasel.time.TimeFormatter.FormatterUnit.Seconds;

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
        return toString(this.verbose);
    }

    public String toString(boolean verbose) {
        return format(verbose ? TimeFormatterImpl.VERBOSE_FORMATTER : TimeFormatterImpl.SIMPLE_FORMATTER);
    }

    public String format(TimeFormatter timeFormatter) {
        String s = Stream.of(new String[]{format(getDays(), timeFormatter.getFormatter(Days)),
                format(getHours(), timeFormatter.getFormatter(Hours)),
                format(getMinutes(), timeFormatter.getFormatter(Minutes)),
                format(getSeconds(), timeFormatter.getFormatter(Seconds)),
                format(getMilliseconds(), timeFormatter.getFormatter(Milliseconds)),
                format(getNanoseconds(), timeFormatter.getFormatter(Nanoseconds))}).
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
