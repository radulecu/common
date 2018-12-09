package ro.rasel.utils.criptography;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class KeyStoreUtils {
    public KeyStore loadKeyStore(File file, char[] keyStorePassword, String keyStoreType)
            throws IOException, NoSuchAlgorithmException, CertificateException,
            KeyStoreException {

        KeyStore keyStore = KeyStore.getInstance(keyStoreType);

        try (FileInputStream stream = new FileInputStream(file)) {
            keyStore.load(stream, keyStorePassword);
        }

        return keyStore;
    }

    public KeyStore storeKeyStore(File file, PrivateKey key, Certificate certificate, String alias,
                                  char[] keyStorePassword, char[] keyPassword, String keyStoreType)
            throws IOException, NoSuchAlgorithmException, CertificateException,
            KeyStoreException {

        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, keyStorePassword);
        if (key != null) {
            keyStore.setKeyEntry(alias, key, keyPassword, new Certificate[]{certificate});
        } else {
            keyStore.setCertificateEntry(alias, certificate);
        }

        try (FileOutputStream stream = new FileOutputStream(file)) {
            keyStore.store(stream, keyStorePassword);
        }

        return keyStore;
    }

    public Certificate loadCertificate(File file, char[] keyStorePassword, String keyStoreType, String alias)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        KeyStore keyStore = loadKeyStore(file, keyStorePassword, keyStoreType);
        return keyStore.getCertificate(alias);
    }

    public KeyStore storeCertificate(File file, Certificate certificate, String alias, char[] keyStorePassword,
                                     String keyStoreType)
            throws IOException, NoSuchAlgorithmException, CertificateException,
            KeyStoreException {

        KeyStore keyStore = KeyStore.getInstance(keyStoreType);

        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                keyStore.load(fileInputStream, keyStorePassword);
            }
        } else {
            keyStore.load(null, keyStorePassword);
        }
        keyStore.setCertificateEntry(alias, certificate);

        try (FileOutputStream stream = new FileOutputStream(file)) {
            keyStore.store(stream, keyStorePassword);
        }

        return keyStore;
    }
}
