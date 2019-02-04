package ro.rasel.utils.criptography;

import org.junit.Test;
import ro.rasel.utils.criptography.keystore.KeyStoreUtils;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class KeyStoreUtilsTest {
    private static final KeyStoreUtils KEY_STORE_UTILS = new KeyStoreUtils();
    private static final char[] KEY_STORE_PASSWORD = "jkspass".toCharArray();
    private static final String ALIAS = "jkslocalhostalias";
    private static final char[] KEY_PASSWORD = "jkspass".toCharArray();
    private static final String KEY_STORE_TYPE = "JKS";
    private static final String JKS_FILE_NAME = "my.jks";
    private static final String TRUSTSTORE_FILE_NAME = "my.truststore";

    @Test
    public void assertThatKeyStoreStorageIsReversible()
            throws KeyStoreException, UnrecoverableKeyException, IOException, CertificateException,
            NoSuchAlgorithmException {

        File file = new File(KeyStoreUtils.class.getResource("/" + JKS_FILE_NAME).getFile());
        KeyStore loadedKeyStore = KEY_STORE_UTILS.loadKeyStore(file, KEY_STORE_PASSWORD, KEY_STORE_TYPE);

        Certificate certificate = loadedKeyStore.getCertificate(ALIAS);
        PrivateKey loadedKey = (PrivateKey) loadedKeyStore.getKey(ALIAS, KEY_PASSWORD);

        assertNotNull(certificate);
        assertNotNull(loadedKey);

        File file2 = new File(file.getParent(), "my2.jks");
        if (file2.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file2.delete();
        }
        KEY_STORE_UTILS
                .storeKeyStore(file2, loadedKey, certificate, ALIAS, KEY_STORE_PASSWORD, KEY_PASSWORD, KEY_STORE_TYPE);
        file2.deleteOnExit();

        KeyStore loadedKeyStore2 = KEY_STORE_UTILS.loadKeyStore(file2, KEY_STORE_PASSWORD, KEY_STORE_TYPE);

        Certificate certificate2 = loadedKeyStore2.getCertificate(ALIAS);
        PrivateKey loadedKey2 = (PrivateKey) loadedKeyStore2.getKey(ALIAS, KEY_PASSWORD);

        assertThat(certificate2, is(certificate));
        assertThat(loadedKey2, is(loadedKey));
    }

    @Test
    public void assertThatTrustStoreStorageIsReversible()
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException,
            UnrecoverableKeyException {
        File file = new File(KeyStoreUtils.class.getResource("/" + TRUSTSTORE_FILE_NAME).getFile());
        Certificate certificate = KEY_STORE_UTILS.loadCertificate(file, KEY_STORE_PASSWORD, KEY_STORE_TYPE, ALIAS);
        assertNotNull(certificate);

        PrivateKey loadedKey = (PrivateKey) KEY_STORE_UTILS.loadKeyStore(file, KEY_STORE_PASSWORD, KEY_STORE_TYPE)
                .getKey(ALIAS, KEY_PASSWORD);
        assertNull(loadedKey);

        File file2 = new File(file.getParent(), "my2.truststore");
        if (file2.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file2.delete();
        }
        KEY_STORE_UTILS.storeCertificate(file2, certificate, ALIAS, KEY_STORE_PASSWORD, KEY_STORE_TYPE);
        file2.deleteOnExit();

        KeyStore loadedKeyStore2 = KEY_STORE_UTILS.loadKeyStore(file2, KEY_STORE_PASSWORD, KEY_STORE_TYPE);
        Certificate certificate2 = loadedKeyStore2.getCertificate(ALIAS);
        assertThat(certificate2, is(certificate));
    }
}