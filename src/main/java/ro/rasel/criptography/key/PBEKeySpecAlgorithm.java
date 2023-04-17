package ro.rasel.criptography.key;

import java.util.Objects;

public class PBEKeySpecAlgorithm {

    private final int iterations;
    private final int keyLength;

    public PBEKeySpecAlgorithm(int iterations, int keyLength) {
        this.iterations = iterations;
        this.keyLength = keyLength;
    }

    public int getIterations() {
        return iterations;
    }

    public int getKeyLength() {
        return keyLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PBEKeySpecAlgorithm that = (PBEKeySpecAlgorithm) o;
        return iterations == that.iterations &&
                keyLength == that.keyLength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iterations, keyLength);
    }

    @Override
    public String toString() {
        return "PBEKeySpecAlgorithm{" +
                "iterations=" + iterations +
                ", keyLength=" + keyLength +
                '}';
    }
}
