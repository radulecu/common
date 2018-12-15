package ro.rasel.time;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * While not adding any new methods the class has the purpose to declare much easier a
 * {@link ITimeInterval}{@literal <Duration, ChronoUnit>}.
 * <br>
 * {@code ITimeInterval formatter;}
 * <br>
 * instead of
 * <br>
 * {@code ITimeFormatter<Duration, ChronoUnit> formatter;
 * }
 */
public interface ITimeInterval extends ITimeFormatter<Duration, ChronoUnit> {
}
