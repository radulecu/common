package ro.rasel.time;

import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.Objects;
import java.util.function.LongFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

public abstract class AbstractTimeInterval<T, U extends TemporalUnit> implements ITimeInterval<T, U> {
    private final Map<U, ToLongFunction<T>> timeProvider;
    private final T t;

    public AbstractTimeInterval(T t, Map<U, ToLongFunction<T>> timeProvider) {
        this.t = t;
        this.timeProvider = timeProvider;
    }

    @Override
    public T getTime() {
        return t;
    }

    @Override
    public long get(U unit) {
        return timeProvider.get(unit).applyAsLong(getTime());
    }

    public String format(ITimeFormatter<U> timeFormatter) {
        String s = timeProvider.keySet().stream()
                .map(unit -> format(getAsInt(unit), timeFormatter.getFormatter(unit)))
                .filter(Objects::nonNull).collect(Collectors.joining(timeFormatter.getSeparator()));
        return s.isEmpty() ? "0" : s;
    }

    private String format(long value, LongFunction<String> function) {
        if (value == 0) {
            return null;
        } else {
            return function.apply(value);
        }
    }
}
