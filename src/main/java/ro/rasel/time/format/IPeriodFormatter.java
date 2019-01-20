package ro.rasel.time.format;

import java.time.Period;
import java.time.temporal.ChronoUnit;

/**
 * While not adding any new methods the class has the purpose to declare much easier a
 * {@link IPeriodFormatter}{@literal <Period, ChronoUnit>}.
 * <br>
 * {@code IPeriodFormatter formatter;}
 * <br>
 * instead of
 * <br>
 * {@code ITemporalAmountFormatter<Duration, ChronoUnit> formatter;
 * }
 */
public interface IPeriodFormatter extends ITemporalAmountFormatter<Period, ChronoUnit> {
}
