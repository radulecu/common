package ro.rasel.utils.criptography;

public interface ICipherAlgorithm {
    String getAlgorithm();

    String getCipherMode();

    String getPadding();

    String getCipherAlgorithm();
}