package ro.rasel.utils.criptography;

public enum CipherMode {
    ECB,
    CBC,
    ;

    public String getMode() {
        return name();
    }
}