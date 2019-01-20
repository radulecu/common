package ro.rasel.time;

import ro.rasel.time.format.DurationFormatter;
import ro.rasel.time.format.IDurationFormatter;

import java.time.Duration;

public class TimerImpl implements Timer {
    private final long nanos = System.currentTimeMillis();
    private final boolean verbose;

    public TimerImpl() {
        this(false);
    }

    public TimerImpl(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public IDurationFormatter getTimePassed() {
        return new DurationFormatter(Duration.ofMillis(System.currentTimeMillis() - nanos), verbose);
    }

    @Override
    public String toString() {
        return getTimePassed().toString();
    }
}
