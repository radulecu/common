package ro.rasel.time;

public interface TimeInterval {
    long getTotalMilliseconds();

    int getMiliseconds();

    int getSeconds();

    int getMinutes();

    int getHours();

    long getDays();

    String toString();
}
