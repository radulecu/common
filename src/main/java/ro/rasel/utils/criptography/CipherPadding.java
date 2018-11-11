package ro.rasel.utils.criptography;

public enum CipherPadding {
    PKCS5Padding,
    PKCS1Padding,
    ;

    public String getPadding() {
        return name();
    }
}