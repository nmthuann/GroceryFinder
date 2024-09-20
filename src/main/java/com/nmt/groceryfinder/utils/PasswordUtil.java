package com.nmt.groceryfinder.utils;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordUtil {

    private  final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordUtil(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Hashes the password using the configured PasswordEncoder.
     *
     * @param password the plain text password
     * @return the hashed password
     */
    public String hashPassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    /**
     * Compares the raw password with the stored hashed password.
     *
     * @param password the plain text password
     * @param storePasswordHash the stored hashed password
     * @return true if the passwords match, false otherwise
     */
    public Boolean comparePassword(String password, String storePasswordHash) {
        return this.passwordEncoder.matches(password, storePasswordHash);
    }


    /**
     * Generates a random password of the specified length using characters from the provided base string.
     *
     * @param length the desired length of the generated password
     * @param base the string containing characters to be used for generating the password
     * @return a randomly generated password
     * @throws IllegalArgumentException if the length is less than or equal to 0 or if the base string is empty
     */
    public String randomPassword(@NotNull int length, @NotNull String base) {
        if (length <= 0) {
            throw new IllegalArgumentException("Password length must be greater than 0");
        }

        if (base.isEmpty()) {
            throw new IllegalArgumentException("Base string must not be empty");
        }

        SecureRandom random = new SecureRandom();
        StringBuilder result = new StringBuilder();

        int baseLength = base.length();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(baseLength);
            result.append(base.charAt(randomIndex));
        }
        return result.toString();
    }
}
