package ro.rasel.time;

public class Timer {
    private long nanos = System.currentTimeMillis();
    private boolean verbose;

    public Timer(boolean verbose) {
        this.verbose = verbose;
    }

    public TimeInterval getTimePassed() {
        return new TimeIntervalImpl(System.currentTimeMillis() - nanos, verbose);
    }

    @Override
    public String toString() {
        return getTimePassed().toString();
    }
}
