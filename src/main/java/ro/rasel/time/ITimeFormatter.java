package ro.rasel.time;

import java.util.function.LongFunction;

public interface ITimeFormatter<T> {
    LongFunction<String> getFormatter(T t);

    String getSeparator();
}
