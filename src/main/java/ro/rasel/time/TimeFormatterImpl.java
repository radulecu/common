package ro.rasel.time;

import java.util.Objects;
import java.util.function.LongFunction;

public class TimeFormatterImpl implements TimeFormatter {
    public static final TimeFormatter SIMPLE_FORMATTER = new TimeIntervalFormatterBuilder()
            .setDaysFormatter(value -> simpleFormat(value, "d"))
            .setHoursFormatter(value -> simpleFormat(value, "h"))
            .setMinutesFormatter(value -> simpleFormat(value, "m"))
            .setSecondsFormatter(value -> simpleFormat(value, "s"))
            .setMillisecondsFormatter(value -> simpleFormat(value, "ms"))
            .setNanosecondsFormatter(value -> simpleFormat(value, "ns"))
            .setSeparator(":")
            .build();
    public static final TimeFormatter VERBOSE_FORMATTER = new TimeIntervalFormatterBuilder()
            .setDaysFormatter(value -> pluralFormat(value, "day", "days"))
            .setHoursFormatter(value -> pluralFormat(value, "hour", "hours"))
            .setMinutesFormatter(value -> pluralFormat(value, "minute", "minutes"))
            .setSecondsFormatter(value -> pluralFormat(value, "second", "seconds"))
            .setMillisecondsFormatter(value -> pluralFormat(value, "millisecond", "milliseconds"))
            .setNanosecondsFormatter(value -> pluralFormat(value, "nanosecond", "nanoseconds"))
            .setSeparator(":")
            .build();

    private final LongFunction<String> daysFormatter;
    private final LongFunction<String> hoursFormatter;
    private final LongFunction<String> minutesFormatter;
    private final LongFunction<String> secondsFormatter;
    private final LongFunction<String> millisecondsFormatter;
    private final LongFunction<String> nanosecondsFormatter;
    private final String separator;

    private TimeFormatterImpl(LongFunction<String> daysFormatter,
                              LongFunction<String> hoursFormatter,
                              LongFunction<String> minutesFormatter,
                              LongFunction<String> secondsFormatter,
                              LongFunction<String> millisecondsFormatter,
                              LongFunction<String> nanosecondsFormatter,
                              String separator) {
        Objects.requireNonNull(daysFormatter, "daysFormatter should not be null");
        Objects.requireNonNull(hoursFormatter, "hoursFormatter should not be null");
        Objects.requireNonNull(minutesFormatter, "minutesFormatter should not be null");
        Objects.requireNonNull(secondsFormatter, "secondsFormatter should not be null");
        Objects.requireNonNull(millisecondsFormatter, "millisecondsFormatter should not be null");
        Objects.requireNonNull(nanosecondsFormatter, "nanosecondsFormatter should not be null");
        Objects.requireNonNull(separator, "separator should not be null");

        this.daysFormatter = daysFormatter;
        this.hoursFormatter = hoursFormatter;
        this.minutesFormatter = minutesFormatter;
        this.secondsFormatter = secondsFormatter;
        this.millisecondsFormatter = millisecondsFormatter;
        this.nanosecondsFormatter = nanosecondsFormatter;
        this.separator = separator;
    }

    public static String simpleFormat(long value, String suffix) {
        return pluralFormat(value, suffix, suffix);
    }

    public static String pluralFormat(long value, String suffix, String pluralSuffix) {
        return value + (value == 1 ? suffix : pluralSuffix);
    }

    @Override
    public LongFunction<String> getDaysFormatter() {
        return daysFormatter;
    }

    @Override
    public LongFunction<String> getHoursFormatter() {
        return hoursFormatter;
    }

    @Override
    public LongFunction<String> getMinutesFormatter() {
        return minutesFormatter;
    }

    @Override
    public LongFunction<String> getSecondsFormatter() {
        return secondsFormatter;
    }

    @Override
    public LongFunction<String> getMillisecondsFormatter() {
        return millisecondsFormatter;
    }

    @Override
    public LongFunction<String> getNanosecondsFormatter() {
        return nanosecondsFormatter;
    }

    @Override
    public String getSeparator() {
        return separator;
    }

    public static class TimeIntervalFormatterBuilder {
        private LongFunction<String> daysFormatter;
        private LongFunction<String> hoursFormatter;
        private LongFunction<String> minutesFormatter;
        private LongFunction<String> secondsFormatter;
        private LongFunction<String> millisecondsFormatter;
        private LongFunction<String> nanosecondsFormatter;
        private String separator;

        public TimeIntervalFormatterBuilder setDaysFormatter(LongFunction<String> daysFormatter) {
            this.daysFormatter = daysFormatter;
            return this;
        }

        public TimeIntervalFormatterBuilder setHoursFormatter(LongFunction<String> hoursFormatter) {
            this.hoursFormatter = hoursFormatter;
            return this;
        }

        public TimeIntervalFormatterBuilder setMinutesFormatter(LongFunction<String> minutesFormatter) {
            this.minutesFormatter = minutesFormatter;
            return this;
        }

        public TimeIntervalFormatterBuilder setSecondsFormatter(LongFunction<String> secondsFormatter) {
            this.secondsFormatter = secondsFormatter;
            return this;
        }

        public TimeIntervalFormatterBuilder setMillisecondsFormatter(LongFunction<String> millisecondsFormatter) {
            this.millisecondsFormatter = millisecondsFormatter;
            return this;
        }

        public TimeIntervalFormatterBuilder setNanosecondsFormatter(LongFunction<String> nanosecondsFormatter) {
            this.nanosecondsFormatter = nanosecondsFormatter;
            return this;
        }

        public TimeIntervalFormatterBuilder setSeparator(String separator) {
            this.separator = separator;
            return this;
        }

        public TimeFormatterImpl build() {
            return new TimeFormatterImpl(daysFormatter, hoursFormatter, minutesFormatter,
                    secondsFormatter, millisecondsFormatter, nanosecondsFormatter, separator);
        }
    }
}
