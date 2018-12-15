package ro.rasel.time;

import java.time.Duration;

public class TimerImpl implements Timer {
    private long nanos = System.currentTimeMillis();
    private boolean verbose;

    public TimerImpl(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public ITimeFormatter getTimePassed() {
        return new TimeInterval(Duration.ofMillis(System.currentTimeMillis() - nanos), verbose);
    }

    @Override
    public String toString() {
        return getTimePassed().toString();
    }
}
