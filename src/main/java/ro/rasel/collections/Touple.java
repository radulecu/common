package ro.rasel.collections;

import java.util.Objects;

public class Touple<P, Q> {
    private final P first;
    private final Q second;

    public Touple(P first, Q second) {
        this.first = first;
        this.second = second;
    }

    public P getFirst() {
        return first;
    }

    public Q getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Touple<?, ?> touple = (Touple<?, ?>) o;
        return Objects.equals(first, touple.first) &&
                Objects.equals(second, touple.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Touple{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
