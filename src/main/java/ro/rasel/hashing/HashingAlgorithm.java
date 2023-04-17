package ro.rasel.hashing;

import java.util.Objects;

public class HashingAlgorithm {
    private final String algorithm;
    private final int saltLength;

    public HashingAlgorithm(String algorithm, int saltLength) {
        this.algorithm = algorithm;
        this.saltLength = saltLength;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public int getSaltLength() {
        return saltLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HashingAlgorithm that = (HashingAlgorithm) o;
        return saltLength == that.saltLength &&
                Objects.equals(algorithm, that.algorithm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm, saltLength);
    }

    @Override
    public String toString() {
        return "HashingAlgorithm{" +
                "algorithm='" + algorithm + '\'' +
                ", saltLength=" + saltLength +
                '}';
    }

}
