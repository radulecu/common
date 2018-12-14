package ro.rasel.time;

import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;

public interface ITimeFormatter {
    LongFunction<String> getFormatter(TimeUnit TimeUnit);

    String getSeparator();
}
