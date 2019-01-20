package ro.rasel.time.format;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * While not adding any new methods the class has the purpose to declare much easier a
 * {@link IDurationFormatter}{@literal <Duration, ChronoUnit>}.
 * <br>
 * {@code IDurationFormatter formatter;}
 * <br>
 * instead of
 * <br>
 * {@code ITemporalAmountFormatter<Duration, ChronoUnit> formatter;
 * }
 */
public interface IDurationFormatter extends ITemporalAmountFormatter<Duration, ChronoUnit> {
}
