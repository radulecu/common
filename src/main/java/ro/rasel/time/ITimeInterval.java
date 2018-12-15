package ro.rasel.time;

import java.time.temporal.TemporalUnit;

public interface ITimeInterval<T, U extends TemporalUnit> {
    T getTime();

    long get(U u);
}
