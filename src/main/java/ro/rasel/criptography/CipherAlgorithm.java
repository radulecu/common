package ro.rasel.criptography;

public interface CipherAlgorithm {
    String getAlgorithm();

    String getCipherBlockMode();

    String getPadding();

    String getCipherAlgorithm();
}