package ro.rasel.time;

public interface ITimeInterval<T, U> {
    T getTime();

    long get(U u);
}
