package ro.rasel.time.format;

import ro.rasel.collections.MapBuilder;
import ro.rasel.format.ILongValueFormatter;
import ro.rasel.format.LongValueFormatter;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MICROS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.time.temporal.ChronoUnit.YEARS;

public class ChronoUnitFormatter implements ITemporalUnitFormatter<ChronoUnit> {
    public static final ITemporalUnitFormatter<ChronoUnit> SIMPLE_FORMATTER = new ChronoUnitFormatter(
            MapBuilder.<ChronoUnit, ILongValueFormatter>ofMap()
                    .put(YEARS, new LongValueFormatter("y"))
                    .put(MONTHS, new LongValueFormatter("mo"))
                    .put(DAYS, new LongValueFormatter("d"))
                    .put(HOURS, new LongValueFormatter("h"))
                    .put(MINUTES, new LongValueFormatter("m"))
                    .put(SECONDS, new LongValueFormatter("s"))
                    .put(MILLIS, new LongValueFormatter("ms"))
                    .put(MICROS, new LongValueFormatter("us"))
                    .put(NANOS, new LongValueFormatter("ns"))
                    .build(),
            ":");
    public static final ITemporalUnitFormatter<ChronoUnit> VERBOSE_FORMATTER = new ChronoUnitFormatter(
            MapBuilder.<ChronoUnit, ILongValueFormatter>ofMap()
                    .put(YEARS, new LongValueFormatter(" year", " years"))
                    .put(MONTHS, new LongValueFormatter(" month", " months"))
                    .put(DAYS, new LongValueFormatter(" day", " days"))
                    .put(HOURS, new LongValueFormatter(" hour", " hours"))
                    .put(MINUTES, new LongValueFormatter(" minute", " minutes"))
                    .put(SECONDS, new LongValueFormatter(" second", " seconds"))
                    .put(MILLIS, new LongValueFormatter(" millisecond", " milliseconds"))
                    .put(MICROS, new LongValueFormatter(" microsecond", " microseconds"))
                    .put(NANOS, new LongValueFormatter(" nanosecond", " nanoseconds"))
                    .build(),
            ":");

    private final String separator;
    private final Map<ChronoUnit, ILongValueFormatter> formatterMap;

    public ChronoUnitFormatter(Map<ChronoUnit, ILongValueFormatter> formatterMap, String separator) {
        Objects.requireNonNull(formatterMap, "formatterMap should not be null");
        Objects.requireNonNull(separator, "separator should not be null");

        this.formatterMap = Collections.unmodifiableMap(new HashMap<>(formatterMap));
        this.separator = separator;
    }

    @Override
    public ILongValueFormatter getFormatter(ChronoUnit chronoUnit) {
        return formatterMap.get(chronoUnit);
    }

    @Override
    public String getSeparator() {
        return separator;
    }
}
