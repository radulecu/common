package ro.rasel.utils.hashing;

public interface Hash {
    byte[] getValue();

    byte[] getSalt();
}