package ro.rasel.utils.hashing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ro.rasel.utils.criptography.key.KeySpecGenerator;
import ro.rasel.utils.criptography.key.PBEKeySpecAlgorithm;
import ro.rasel.utils.criptography.key.PBEKeySpecGenerator;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class SecretKeyFactoryHashGeneratorTest {
    private static final List<String> TEST_STRINGS =
            Arrays.asList("test", "",
                    "longStringWithVariousCharacters sdfaskf897f2bjefh92289f92h3ufhsdjfsdhifuduigd88*&^*&^^%$%&+*FGFGFCF><>><M><~");
    private static final int ITERATIONS = 1000;
    private final HashGenerator hashGenerator;
    private final String string;
    private final KeySpecGenerator keySpecGenerator;

    public SecretKeyFactoryHashGeneratorTest(HashingAlgorithm hashingAlgorithm, String string)
            throws NoSuchAlgorithmException {
        keySpecGenerator = new PBEKeySpecGenerator(new PBEKeySpecAlgorithm(ITERATIONS, 512));
        this.hashGenerator = new SecretKeyFactoryHashGenerator(hashingAlgorithm,
                keySpecGenerator);
        this.string = string;
    }

    @Parameters
    public static Collection<Object[]> data() {
        // used stream of for the possibility to add easier new algorithms for testing
        return Stream.of(SecretKeyFactoryAlgorithm.values())
                .map(secretKeyFactoryAlgorithm ->
                        new HashingAlgorithm(secretKeyFactoryAlgorithm.toString(), 64))
                .flatMap(secretKeyFactoryAlgorithm ->
                        TEST_STRINGS.stream()
                                .map(string -> new Object[]{secretKeyFactoryAlgorithm, string}))
                .collect(Collectors.toList());
    }

    @Test(expected = RuntimeException.class)
    public void nullAlgorithmShouldThrowExceptionInConstructor() throws NoSuchAlgorithmException {
        new SecretKeyFactoryHashGenerator(null,keySpecGenerator);
    }

    @Test(expected = RuntimeException.class)
    public void nullKeySpecGeneratorShouldThrowExceptionInConstructor() throws NoSuchAlgorithmException {
        new SecretKeyFactoryHashGenerator(hashGenerator.getHashingAlgorithm(),null);
    }

    @Test
    public void shouldNotBeNull() throws InvalidKeySpecException {
        Hash hash = hashGenerator.generateHash(string);
        assertNotNull(hash.getValue());
        assertNotNull(hash.getSalt());

        byte[] salt = hashGenerator.generateSalt();
        assertNotNull(salt);
    }

    @Test
    public void saltGenerationShouldBeUniqueForEachGeneration() throws InvalidKeySpecException {
        Hash hash = hashGenerator.generateHash(string);
        Hash hash2 = hashGenerator.generateHash(string);
        assertNotEquals(hash.getSalt(), hash2.getSalt());

        byte[] salt = hashGenerator.generateSalt();
        byte[] salt2 = hashGenerator.generateSalt();
        assertNotEquals(salt, salt2);

        byte[] hashBytes = hashGenerator.generateHash(string, salt);
        byte[] hashBytes2 = hashGenerator.generateHash(string, salt2);
        assertFalse(Arrays.equals(hashBytes, hashBytes2));
    }

    @Test
    public void hashGenerationShouldBeDifferentForDifferentSalts() throws InvalidKeySpecException {
        byte[] salt = hashGenerator.generateSalt();
        byte[] salt2 = hashGenerator.generateSalt();
        byte[] hashBytes = hashGenerator.generateHash(string, salt);
        byte[] hashBytes2 = hashGenerator.generateHash(string, salt2);
        assertFalse(Arrays.equals(hashBytes, hashBytes2));
    }

    @Test
    public void hashGenerationShouldBeDifferentForEachGeneration() throws InvalidKeySpecException {
        Hash hash = hashGenerator.generateHash(string);
        Hash hash2 = hashGenerator.generateHash(string);
        assertFalse(Arrays.equals(hash.getValue(), hash2.getValue()));
    }

    @Test
    public void hashGenerationShouldBeEqualForSameSalt() throws InvalidKeySpecException {
        byte[] salt = hashGenerator.generateSalt();
        byte[] hashBytes = hashGenerator.generateHash(string, salt);
        byte[] hashBytes2 = hashGenerator.generateHash(string, salt);
        assertArrayEquals(hashBytes, hashBytes2);
    }

    @Test
    public void nullTextShouldReturnNullValue() throws InvalidKeySpecException {
        byte[] salt = hashGenerator.generateSalt();

        assertNull(hashGenerator.generateHash(null));
        assertNull(hashGenerator.generateHash(null, salt));
    }

    @Test(expected = RuntimeException.class)
    public void nullSaltShouldThrowException() throws InvalidKeySpecException {
        hashGenerator.generateHash(string, null);
    }

    @Test(expected = RuntimeException.class)
    public void nullTextAndNullSaltShouldThrowException() throws InvalidKeySpecException {
        hashGenerator.generateHash(null, null);
    }
}