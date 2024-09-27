package com.nmt.groceryfinder.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/27/2024
 */
public class PasswordUtilTest {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final PasswordUtil passwordUtil = new PasswordUtil(passwordEncoder);

    @Test
    void testHashPassword() {
        String rawPassword = "password123";
        String hashedPassword = passwordUtil.hashPassword(rawPassword);
        assertNotNull(hashedPassword);
        assertNotEquals(rawPassword, hashedPassword);
    }

    @Test
    void testComparePassword() {
        String rawPassword = "password123";
        String hashedPassword = passwordUtil.hashPassword(rawPassword);
        assertTrue(passwordUtil.comparePassword(rawPassword, hashedPassword));
    }

    @Test
    void testRandomPassword() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String randomPassword = passwordUtil.randomPassword(12, base);
        assertNotNull(randomPassword);
        assertEquals(12, randomPassword.length());
    }

    @Test
    void testRandomPasswordInvalidLength() {
        String base = "ABCDE";
        assertThrows(IllegalArgumentException.class, () -> passwordUtil.randomPassword(0, base));
        assertThrows(IllegalArgumentException.class, () -> passwordUtil.randomPassword(-5, base));
    }

    @Test
    void testRandomPasswordEmptyBase() {
        assertThrows(IllegalArgumentException.class, () -> passwordUtil.randomPassword(10, ""));
    }
}
