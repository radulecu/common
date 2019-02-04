package ro.rasel.utils.criptography;

public interface CipherAlgorithm {
    String getAlgorithm();

    String getCipherBlockMode();

    String getPadding();

    String getCipherAlgorithm();
}