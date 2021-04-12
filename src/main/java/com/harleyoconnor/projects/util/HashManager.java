package com.harleyoconnor.projects.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 * This class handles hashing and authenticating strings.
 *
 * @author Harley O'Connor
 */
public final class HashManager {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final SecretKeyFactory keyFactory;

    private final int size;
    private final int cost;

    /**
     * Creates a new {@link HashManager} with the default hashing algorithm
     * ({@code PBKDF2WithHmacSHA1}), the default {@link #size} ({@code 128}),
     * and the default {@link #cost} ({@code 65536}).
     *
     * @see #HashManager(String, int, int)
     */
    public HashManager() {
        this("PBKDF2WithHmacSHA1", 128, 65536);
    }

    /**
     * Creates a new {@link HashManager} with a custom {@code hashAlgorithm},
     * {@link #size}, and {@link #cost}.
     *
     * @param hashAlgorithm The name of the {@code hashAlgorithm} to use.
     * @param size The size to use for hashing.
     * @param cost The cost to use for hashing.
     * @throws RuntimeException If attempt to get hashing algorithm from given
     *                          name threw {@link NoSuchAlgorithmException}.
     * @see #HashManager()
     */
    public HashManager(String hashAlgorithm, int size, int cost) {
        try {
            this.keyFactory = SecretKeyFactory.getInstance(hashAlgorithm);
        } catch (final NoSuchAlgorithmException e) {
            // This should never happen, but but if it does throw an exception.
            throw new RuntimeException("Couldn't find algorithm '" + hashAlgorithm + "'.", e);
        }

        this.size = size;
        this.cost = cost;
    }

    /**
     * Hashes the the given {@code str}.
     *
     * @param str The {@code str} to hash.
     * @return The hashed {@code str}.
     */
    public String hash (final String str) {
        // Create the salt object, with the amount of bytes set to the size constant.
        byte[] salt = new byte[this.size / 8];

        // Generate random bytes for the salt.
        SECURE_RANDOM.nextBytes(salt);

        // Generate the hashed string from the str char array and random salt.
        byte[] hashedString = this.generateHash(str.toCharArray(), salt);

        byte[] hash = new byte[salt.length + hashedString.length];

        // Copy the salt and hashedString to the hash.
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(hashedString, 0, hash, salt.length, hashedString.length);

        // Use Base64 encoder to store the bytes as a string.
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Checks if the given {@code str} matches the given {@code strIn}.
     *
     * @param str The hashed {@code str} to check against.
     * @param strIn The input {@code str} to check.
     * @return {@code true} if the {@code strIn} matches the hash stored;
     *         {@code false} if not.
     */
    public boolean authenticate (final String str, final String strIn) {
        // Decode the Base64 string into bytes.
        byte[] hash = Base64.getDecoder().decode(str);

        // Get the salt from the hash.
        byte[] salt = Arrays.copyOfRange(hash, 0, this.size / 8);

        // Generate a new hash from the salt stored and str input.
        byte[] hashFromIn = this.generateHash(strIn.toCharArray(), salt);

        // Prevents out of bounds error when str is invalid.
        if (salt.length + hashFromIn.length - 1 > hash.length)
            return false;

        // Check the hashes are equal.
        for (int idx = 0; idx < hashFromIn.length; ++idx) {
            if ((hash[salt.length + idx] ^ hashFromIn[idx]) != 0)
                return false;
        }

        return true;
    }

    /**
     * Generates a new hash from the given character based on the given
     * {@code salt}.
     *
     * @param charsToHash The characters to create a hash from.
     * @param salt The {@code salt} to use.
     * @return The generated hash.
     */
    private byte[] generateHash (char[] charsToHash, byte[] salt) {
        // Create KeySpec object from the characters and salt with the cost and key length constants.
        final KeySpec spec = new PBEKeySpec(charsToHash, salt, this.cost, this.size);

        try {
            // Generate the hash from the secret key factory.
            return this.keyFactory.generateSecret(spec).getEncoded();
        } catch (final InvalidKeySpecException e) {
            throw new RuntimeException("Unable to generate hash.", e);
        }
    }

}