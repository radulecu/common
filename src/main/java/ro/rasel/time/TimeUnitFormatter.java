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
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.time.temporal.ChronoUnit.YEARS;

public class TimeUnitFormatter implements ITimeUnitFormatter<ChronoUnit> {
    public static final ITimeUnitFormatter<ChronoUnit> SIMPLE_FORMATTER = new TimeUnitFormatter(
            MapBuilder.<ChronoUnit, LongFunction<String>>ofMap()
                    .put(YEARS, value -> format(value, "y"))
                    .put(MONTHS, value -> format(value, "mo"))
                    .put(DAYS, value -> format(value, "d"))
                    .put(HOURS, value -> format(value, "h"))
                    .put(MINUTES, value -> format(value, "m"))
                    .put(SECONDS, value -> format(value, "s"))
                    .put(MILLIS, value -> format(value, "ms"))
                    .put(MICROS, value -> format(value, "us"))
                    .put(NANOS, value -> format(value, "ns"))
                    .build(),
            ":");
    public static final ITimeUnitFormatter<ChronoUnit> VERBOSE_FORMATTER = new TimeUnitFormatter(
            MapBuilder.<ChronoUnit, LongFunction<String>>ofMap()
                    .put(YEARS, value -> format(value, " year", " years"))
                    .put(MONTHS, value -> format(value, " month", " months"))
                    .put(DAYS, value -> format(value, " day", " days"))
                    .put(HOURS, value -> format(value, " hour", " hours"))
                    .put(MINUTES, value -> format(value, " minute", " minutes"))
                    .put(SECONDS, value -> format(value, " second", " seconds"))
                    .put(MILLIS, value -> format(value, " millisecond", " milliseconds"))
                    .put(MICROS, value -> format(value, " microsecond", " microseconds"))
                    .put(NANOS, value -> format(value, " nanosecond", " nanoseconds"))
                    .build(),
            ":");

    private final String separator;
    private final Map<ChronoUnit, LongFunction<String>> formatterMap;

    public TimeUnitFormatter(Map<ChronoUnit, LongFunction<String>> formatterMap, String separator) {
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

    public static String format(long value, String unit) {
        return format(value, unit, unit);
    }

    public static String format(long value, String unit, String pluralUnit) {
        return value + (value == 1 ? unit : pluralUnit);
    }
}
