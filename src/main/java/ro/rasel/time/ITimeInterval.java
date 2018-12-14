package ro.rasel.time;

public interface ITimeInterval<T> {
    T getTime();

    int getNanoseconds();

    int getMilliseconds();

    int getSeconds();

    int getMinutes();

    int getHours();

    long getDays();

    String toString();
}
