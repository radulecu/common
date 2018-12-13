package ro.rasel.time;

import ro.rasel.collections.MapBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.LongFunction;
import java.util.stream.Stream;

import static ro.rasel.time.TimeFormatter.FormatterUnit.Days;
import static ro.rasel.time.TimeFormatter.FormatterUnit.Hours;
import static ro.rasel.time.TimeFormatter.FormatterUnit.Milliseconds;
import static ro.rasel.time.TimeFormatter.FormatterUnit.Minutes;
import static ro.rasel.time.TimeFormatter.FormatterUnit.Nanoseconds;
import static ro.rasel.time.TimeFormatter.FormatterUnit.Seconds;

public class TimeFormatterImpl implements TimeFormatter {
    public static final TimeFormatter SIMPLE_FORMATTER = new TimeFormatterImpl(
            MapBuilder.<FormatterUnit, LongFunction<String>>ofMap()
                    .put(Days, value -> simpleFormat(value, "d"))
                    .put(Hours, value -> simpleFormat(value, "h"))
                    .put(Minutes, value -> simpleFormat(value, "m"))
                    .put(Seconds, value -> simpleFormat(value, "s"))
                    .put(Milliseconds, value -> simpleFormat(value, "ms"))
                    .put(Nanoseconds, value -> simpleFormat(value, "ns"))
                    .build(HashMap::new),
            ":");
    public static final TimeFormatter VERBOSE_FORMATTER = new TimeFormatterImpl(
            MapBuilder.<FormatterUnit, LongFunction<String>>ofMap()
                    .put(Days, value -> pluralFormat(value, "day", "days"))
                    .put(Hours, value -> pluralFormat(value, "hour", "hours"))
                    .put(Minutes, value -> pluralFormat(value, "minute", "minutes"))
                    .put(Seconds, value -> pluralFormat(value, "second", "seconds"))
                    .put(Milliseconds, value -> pluralFormat(value, "millisecond", "milliseconds"))
                    .put(Nanoseconds, value -> pluralFormat(value, "nanosecond", "nanoseconds"))
                    .build(HashMap::new),
            ":");

    private final String separator;
    private final Map<FormatterUnit, LongFunction<String>> formatterMap;

    private TimeFormatterImpl(Map<FormatterUnit, LongFunction<String>> formatterMap, String separator) {
        Stream.of(FormatterUnit.values()).forEach(formatterUnit -> Objects
                .requireNonNull(formatterMap.get(formatterUnit),
                        formatterUnit.name().toLowerCase() + " Formatter should not be null"));
        Objects.requireNonNull(separator, "separator should not be null");

        this.formatterMap = Collections.unmodifiableMap(new HashMap<>(formatterMap));
        this.separator = separator;
    }

    public static String simpleFormat(long value, String suffix) {
        return pluralFormat(value, suffix, suffix);
    }

    public static String pluralFormat(long value, String suffix, String pluralSuffix) {
        return value + (value == 1 ? suffix : pluralSuffix);
    }

    @Override
    public LongFunction<String> getFormatter(FormatterUnit formatterUnit) {
        return formatterMap.get(formatterUnit);
    }

    @Override
    public String getSeparator() {
        return separator;
    }
}
