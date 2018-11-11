package ro.rasel.utils.criptography;

public interface ICipherAlgorithm {
    String getAlgorithm();

    CipherMode getCipherMode();

    CipherPadding getPadding();

    String getCipherAlgorithm();
}