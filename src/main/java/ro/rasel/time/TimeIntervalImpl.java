package ro.rasel.time;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeIntervalImpl implements TimeInterval {
    private final long totalMilliseconds;
    private final boolean verbose;

    public TimeIntervalImpl(long totalMilliseconds) {
        this(totalMilliseconds, false);
    }

    public TimeIntervalImpl(long totalMilliseconds, boolean verbose) {
        if (totalMilliseconds < 0) {
            throw new IllegalArgumentException("totalMilliseconds must be positive");
        }

        this.totalMilliseconds = totalMilliseconds;
        this.verbose = verbose;
    }

    @Override
    public long getTotalMilliseconds() {
        return totalMilliseconds;
    }

    @Override
    public int getMiliseconds() {
        return (int) (totalMilliseconds % 1000);
    }

    @Override
    public int getSeconds() {
        return (int) ((totalMilliseconds / 1000) % 60);
    }

    @Override
    public int getMinutes() {
        return (int) ((totalMilliseconds / 1000 / 60) % 60);
    }

    @Override
    public int getHours() {
        return (int) ((totalMilliseconds / 1000 / 60 / 60) % 60);
    }

    @Override
    public long getDays() {
        return (totalMilliseconds / 1000 / 60 / 60 / 24);
    }

    private String toString(long value, String suffix) {
        return toString(value, suffix, suffix);
    }

    private String toString(long value, String suffix, String pluralSuffix) {
        if (value == 1) {
            return value + suffix;
        } else if (value > 1) {
            return value + pluralSuffix;
        }

        return null;
    }

    private String toStringSimple() {
        return Stream.of(
                toString(getDays(), "d"),
                toString(getHours(), "h"),
                toString(getMinutes(), "m"),
                toString(getSeconds(), "s"),
                toString(getMiliseconds(), "ms")
        ).filter(Objects::nonNull).collect(Collectors.joining(":"));
    }

    private String toStringVerbose() {
        return Stream.of(
                toString(getDays(), "day", "days"),
                toString(getHours(), "hour", "hours"),
                toString(getMinutes(), "minute", "minutes"),
                toString(getSeconds(), "second", "seconds"),
                toString(getMiliseconds(), "millisecond", "milliseconds")
        ).filter(Objects::nonNull).collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        String s = verbose ? toStringVerbose() : toStringSimple();
        return s.isEmpty() ? "0" : s;
    }

}
