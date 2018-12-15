package ro.rasel.time;

import ro.rasel.collections.MapBuilder;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.LongFunction;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MICROS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;

public class TimeFormatter implements ITimeFormatter<ChronoUnit> {
    public static final ITimeFormatter<ChronoUnit> SIMPLE_FORMATTER = new TimeFormatter(
            MapBuilder.<ChronoUnit, LongFunction<String>>ofMap()
                    .put(DAYS, value -> simpleFormat(value, "d"))
                    .put(HOURS, value -> simpleFormat(value, "h"))
                    .put(MINUTES, value -> simpleFormat(value, "m"))
                    .put(SECONDS, value -> simpleFormat(value, "s"))
                    .put(MILLIS, value -> simpleFormat(value, "ms"))
                    .put(MICROS, value -> simpleFormat(value, "us"))
                    .put(NANOS, value -> simpleFormat(value, "ns"))
                    .build(),
            ":");
    public static final ITimeFormatter<ChronoUnit> VERBOSE_FORMATTER = new TimeFormatter(
            MapBuilder.<ChronoUnit, LongFunction<String>>ofMap()
                    .put(DAYS, value -> pluralFormat(value, " day", " days"))
                    .put(HOURS, value -> pluralFormat(value, " hour", " hours"))
                    .put(MINUTES, value -> pluralFormat(value, " minute", " minutes"))
                    .put(SECONDS, value -> pluralFormat(value, " second", " seconds"))
                    .put(MILLIS, value -> pluralFormat(value, " millisecond", " milliseconds"))
                    .put(MICROS, value -> pluralFormat(value, " microsecond", " microseconds"))
                    .put(NANOS, value -> pluralFormat(value, " nanosecond", " nanoseconds"))
                    .build(),
            ":");

    private final String separator;
    private final Map<ChronoUnit, LongFunction<String>> formatterMap;

    public TimeFormatter(Map<ChronoUnit, LongFunction<String>> formatterMap, String separator) {
        Objects.requireNonNull(formatterMap, "formatterMap should not be null");
        Objects.requireNonNull(separator, "separator should not be null");

        this.formatterMap = Collections.unmodifiableMap(formatterMap);
        this.separator = separator;
    }

    @Override
    public LongFunction<String> getFormatter(ChronoUnit chronoUnit) {
        return formatterMap.get(chronoUnit);
    }

    @Override
    public String getSeparator() {
        return separator;
    }

    public static String simpleFormat(long value, String unit) {
        return pluralFormat(value, unit, unit);
    }

    public static String pluralFormat(long value, String unit, String pluralUnit) {
        return value + (value == 1 ? unit : pluralUnit);
    }
}
