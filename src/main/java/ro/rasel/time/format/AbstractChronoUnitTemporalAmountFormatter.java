package ro.rasel.time.format;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToLongFunction;

public class AbstractChronoUnitTemporalAmountFormatter<T extends TemporalAmount>
        extends AbstractTemporalAmountFormatter<T, ChronoUnit> {
    public AbstractChronoUnitTemporalAmountFormatter(T t,
            Map<ChronoUnit, ToLongFunction<T>> temporalAmountToChronoUnitConverter) {
        super(t, temporalAmountToChronoUnitConverter);
    }

    public String toString(boolean verbose) {
        return format(verbose ? ChronoUnitFormatter.VERBOSE_FORMATTER : ChronoUnitFormatter.SIMPLE_FORMATTER);
    }

    protected static <T> T parse(String s, Collection<ChronoUnit> chronoUnits,
            Function<Map<ChronoUnit, Long>, T> chronoUnitMapToTemporalAmountConverter, boolean verbose) {
        return parse(s, chronoUnits, chronoUnitMapToTemporalAmountConverter,
                verbose ? ChronoUnitFormatter.VERBOSE_FORMATTER : ChronoUnitFormatter.SIMPLE_FORMATTER);
    }
}
