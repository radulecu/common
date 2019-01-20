package ro.rasel.utils.criptography;

public enum CipherMode {
    ENCRYPT_MODE(1),
    DECRYPT_MODE(2),
    ;

    private final int mode;

    CipherMode(int mode) {
        this.mode = mode;
    }

    int getMode() {
        return mode;
    }
}