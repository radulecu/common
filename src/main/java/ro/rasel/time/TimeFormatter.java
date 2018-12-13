package ro.rasel.time;

import java.util.function.LongFunction;

public interface TimeFormatter {
    LongFunction<String> getFormatter(FormatterUnit formatterUnit);

    String getSeparator();

    enum FormatterUnit {
        Days,
        Minutes,
        Hours,
        Seconds,
        Nanoseconds,
        Milliseconds,
        ;
    }
}
