package ro.rasel.time.format;

import ro.rasel.collections.Touple;
import ro.rasel.format.ILongValueFormatter;

import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractTemporalAmountFormatter<T extends TemporalAmount, U extends TemporalUnit> implements
        ITemporalAmountFormatter<T, U> {
    private final Map<U, ToLongFunction<T>> temporalAmountToChronoUnitConverter;
    private final T t;

    public AbstractTemporalAmountFormatter(T t, Map<U, ToLongFunction<T>> temporalAmountToChronoUnitConverter) {
        this.t = t;
        this.temporalAmountToChronoUnitConverter = temporalAmountToChronoUnitConverter;
    }

    @Override
    public T getTime() {
        return t;
    }

    @Override
    public long get(U unit) {
        return temporalAmountToChronoUnitConverter.get(unit).applyAsLong(getTime());
    }

    public String format(ITemporalUnitFormatter<U> temporalUnitFormatter) {
        String s = temporalAmountToChronoUnitConverter.keySet().stream()
                .map(unit -> format(get(unit), temporalUnitFormatter.getFormatter(unit)))
                .filter(Objects::nonNull).collect(Collectors.joining(temporalUnitFormatter.getSeparator()));
        return s.isEmpty() ? "0" : s;
    }

    private String format(long value, ILongValueFormatter function) {
        if (value == 0) {
            return null;
        } else {
            return function.format(value);
        }
    }

    protected static <T, U extends TemporalUnit> T parse(String value, Collection<U> temporalUnits,
            Function<Map<U, Long>, T> chronoUnitMapToTemporalAmountConverter,
            ITemporalUnitFormatter<U> temporalUnitFormatter) {
        return chronoUnitMapToTemporalAmountConverter.apply(parseAsMap(value, temporalUnits, temporalUnitFormatter));
    }

    protected static <U extends TemporalUnit> Map<U, Long> parseAsMap(String value, Collection<U> temporalUnits,
            ITemporalUnitFormatter<U> temporalUnitFormatter) {
        Objects.requireNonNull(value, "value argument should not be null");
        if ("0".equals(value.trim())) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(Stream.of(value.split(temporalUnitFormatter.getSeparator()))
                .map(s -> temporalUnits.stream()
                        .map(u -> new Touple<>(u, temporalUnitFormatter.getFormatter(u)))
                        .map((Touple<U, ILongValueFormatter> element) -> new Touple<>(element.getFirst(),
                                element.getSecond().parse(s)))
                        .filter(obj -> Objects.nonNull(obj.getSecond()))
                        .findAny()
                        .orElseThrow(() -> new IllegalArgumentException(
                                "cannot parse element " + s + " for value " + value)))
                .collect(Collectors.toMap(Touple::getFirst, Touple::getSecond)));
    }
}
