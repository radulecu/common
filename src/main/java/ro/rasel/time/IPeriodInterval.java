package ro.rasel.time;

import java.time.Period;
import java.time.temporal.ChronoUnit;

/**
 * While not adding any new methods the class has the purpose to declare much easier a
 * {@link IPeriodInterval}{@literal <Period, ChronoUnit>}.
 * <br>
 * {@code IPeriodInterval formatter;}
 * <br>
 * instead of
 * <br>
 * {@code ITimeFormatter<Duration, ChronoUnit> formatter;
 * }
 */
public interface IPeriodInterval extends ITimeFormatter<Period, ChronoUnit> {
}
