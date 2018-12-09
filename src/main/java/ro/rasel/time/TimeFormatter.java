package ro.rasel.time;

import java.util.function.LongFunction;

public interface TimeFormatter {
    LongFunction<String> getDaysFormatter();

    LongFunction<String> getHoursFormatter();

    LongFunction<String> getMinutesFormatter();

    LongFunction<String> getSecondsFormatter();

    LongFunction<String> getMillisecondsFormatter();

    LongFunction<String> getNanosecondsFormatter();

    String getSeparator();
}
