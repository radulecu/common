package ro.rasel.hashing;

public class HashImpl implements Hash {
    private final byte[] value;
    private final byte[] salt;

    public HashImpl(byte[] value, byte[] salt) {
        this.value = value;
        this.salt = salt;
    }

    @Override
    public byte[] getValue() {
        return value;
    }

    @Override
    public byte[] getSalt() {
        return salt;
    }
}
