package ro.rasel.time;

import java.time.Duration;

public interface TimeInterval {
    Duration getDuration();

    int getNanoseconds();

    int getMilliseconds();

    int getSeconds();

    int getMinutes();

    int getHours();

    long getDays();

    String toString();
}
