package ro.rasel.time;

import java.time.temporal.TemporalUnit;

public interface ITimeFormatter<T, U extends TemporalUnit> {
    T getTime();

    long get(U u);

    /**
     * Depending on the way time is represented some of the values are guaranteed to be ints. as a result this method
     * is safe to be used and has the purpose to do the cast to int.
     *
     * @param u
     * @return
     */

    default int getAsInt(U unit) {
        long l = get(unit);
        if (l > Integer.MAX_VALUE) {
            throw new RuntimeException("Cannot cast to int, Usage of long value is recommended");
        }
        return (int) l;
    }
}
