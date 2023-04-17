package ro.rasel.hashing;

public interface Hash {
    byte[] getValue();

    byte[] getSalt();
}