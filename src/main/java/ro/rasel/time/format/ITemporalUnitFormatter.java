package ro.rasel.time.format;

import ro.rasel.format.ILongValueFormatter;

import java.time.temporal.TemporalUnit;

public interface ITemporalUnitFormatter<U extends TemporalUnit> {
    ILongValueFormatter getFormatter(U u);

    String getSeparator();
}
