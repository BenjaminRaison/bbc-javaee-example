package ch.bbcag.javaee.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hash passwords for storage, and test passwords against password tokens.
 * <p>
 * Instances of this class can be used concurrently by multiple threads.
 *
 * @author erickson, slightly adapted by BenjaminRaison
 * @see <a href="http://stackoverflow.com/a/2861125/3474">StackOverflow</a>
 */
public final class PasswordUtil {

    /**
     * Each token produced by this class uses this identifier as a prefix.
     */
    public static final String ID = "$31$";
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int SIZE = 128;
    private static final Pattern layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})");
    private static final SecureRandom random = new SecureRandom();

    /**
     * The cost to use, defined in configuration, 0 to 30. Minimum recommended value: 16
     */
    private static int getCost() {
        return ConfigHandler.getInstance().getPBKDF2Cost();
    }

    /**
     * Calculates the amount of iterations based on cost
     *
     * @param cost the exponential computational cost of hashing a password, 0 to 30
     * @return the amount of iterations
     */
    private static int iterations(int cost) {
        if ((cost < 0) || (cost > 30)) {
            throw new IllegalArgumentException("Invalid cost: " + cost);
        }
        return 1 << cost;
    }

    /**
     * Hash a password for storage.
     *
     * @return a secure authentication token to be stored for later authentication
     */
    public static String hash(char[] password) {
        byte[] salt = new byte[SIZE / 8];
        random.nextBytes(salt);
        byte[] dk = pbkdf2(password, salt, iterations(getCost()));
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        return ID + getCost() + '$' + enc.encodeToString(hash);
    }

    /**
     * Authenticate with a password and a stored password token.
     *
     * @return true if the password and token match
     */
    public static boolean isValid(char[] password, String token) {
        Matcher m = layout.matcher(token);
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid token format");
        }
        int iterations = iterations(Integer.parseInt(m.group(1)));
        byte[] hash = Base64.getUrlDecoder().decode(m.group(2));
        byte[] salt = Arrays.copyOfRange(hash, 0, SIZE / 8);
        byte[] check = pbkdf2(password, salt, iterations);
        int zero = 0;
        for (int idx = 0; idx < check.length; ++idx)
            zero |= hash[salt.length + idx] ^ check[idx];
        return zero == 0;
    }

    /**
     * Implementation of the PBKDF2 key derivation algorithm
     *
     * @param password   the password to hash
     * @param salt       the salt to add to the password
     * @param iterations the number of iterations. Higher is better. Should be between 2^0 and 2^32
     * @return the hash as a byte[]
     * @see <a href="https://en.wikipedia.org/wiki/PBKDF2">PBKDF2 on Wikipedia</a>
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
        KeySpec spec = new PBEKeySpec(password, salt, iterations, SIZE);
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Missing algorithm: " + ALGORITHM, ex);
        } catch (InvalidKeySpecException ex) {
            throw new IllegalStateException("Invalid SecretKeyFactory", ex);
        }
    }

    /**
     * Hash a password in an immutable {@code String}.
     * <p>
     * <p>Passwords should be stored in a {@code char[]} so that it can be filled
     * with zeros after use instead of lingering on the heap and elsewhere.</p>
     *
     * @deprecated Use {@link #hash(char[])} instead
     */
    @Deprecated
    public static String hash(String password) {
        return hash(password.toCharArray());
    }

    /**
     * Authenticate with a password in an immutable {@code String} and a stored
     * password token.
     * <p>
     * <p>Passwords should be stored in a {@code char[]} so that it can be filled
     * with zeros after use instead of lingering on the heap and elsewhere.</p>
     *
     * @see #hash(String)
     * @deprecated Use {@link #isValid(char[], String)} instead.
     */
    @Deprecated
    public static boolean isValid(String password, String token) {
        return isValid(password.toCharArray(), token);
    }

}