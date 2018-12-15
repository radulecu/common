package ro.rasel.time;

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
    public ITimeInterval getTimePassed() {
        return new TimeInterval(Duration.ofMillis(System.currentTimeMillis() - nanos), verbose);
    }

    @Override
    public String toString() {
        return getTimePassed().toString();
    }
}
