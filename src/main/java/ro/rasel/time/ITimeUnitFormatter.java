package ro.rasel.time;

import java.time.temporal.TemporalUnit;
import java.util.function.LongFunction;

public interface ITimeUnitFormatter<U extends TemporalUnit> {
    LongFunction<String> getFormatter(U u);

    String getSeparator();
}
