package ro.rasel.time;

import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;

public interface TimeFormatter {
    LongFunction<String> getFormatter(TimeUnit TimeUnit);

    String getSeparator();
}
