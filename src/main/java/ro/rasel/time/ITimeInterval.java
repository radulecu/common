package ro.rasel.time;

import java.time.Duration;

public interface ITimeInterval {
    Duration getDuration();

    int getNanoseconds();

    int getMilliseconds();

    int getSeconds();

    int getMinutes();

    int getHours();

    long getDays();

    String toString();
}
