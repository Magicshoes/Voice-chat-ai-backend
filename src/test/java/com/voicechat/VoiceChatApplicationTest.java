package com.voicechat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("VoiceChatApplication Tests")
class VoiceChatApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ==================== Application Context Tests ====================

    @Test
    @DisplayName("Should create application context successfully")
    void testApplicationContextCreation() {
        assertNotNull(applicationContext);
    }

    @Test
    @DisplayName("Should load VoiceChatApplication as Spring Boot application")
    void testApplicationLoads() {
        assertNotNull(applicationContext.getBean(VoiceChatApplication.class));
    }

    @Test
    @DisplayName("Should create BCryptPasswordEncoder bean")
    void testPasswordEncoderBeanCreation() {
        assertNotNull(passwordEncoder);
    }

    // ==================== BCryptPasswordEncoder Bean Tests ====================

    @Test
    @DisplayName("Should encode password correctly")
    void testPasswordEncodingWorks() {
        String rawPassword = "testPassword123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
    }

    @Test
    @DisplayName("Should match raw password with encoded password")
    void testPasswordMatching() {
        String rawPassword = "testPassword123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    @DisplayName("Should not match incorrect password with encoded password")
    void testPasswordMismatch() {
        String rawPassword = "testPassword123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertFalse(passwordEncoder.matches("wrongPassword", encodedPassword));
    }

    @Test
    @DisplayName("Should encode same password differently each time")
    void testPasswordEncodingGeneratesDifferentHashesEachTime() {
        String rawPassword = "testPassword123";
        String encodedPassword1 = passwordEncoder.encode(rawPassword);
        String encodedPassword2 = passwordEncoder.encode(rawPassword);
        
        assertNotEquals(encodedPassword1, encodedPassword2);
    }

    @Test
    @DisplayName("Should match same password with different hashes")
    void testBothHashesMatchSamePassword() {
        String rawPassword = "testPassword123";
        String encodedPassword1 = passwordEncoder.encode(rawPassword);
        String encodedPassword2 = passwordEncoder.encode(rawPassword);
        
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword1));
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword2));
    }

    @Test
    @DisplayName("Should handle empty string passwords")
    void testEmptyStringPassword() {
        String rawPassword = "";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    @DisplayName("Should handle long passwords")
    void testLongPassword() {
        String rawPassword = "a".repeat(100);
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    @DisplayName("Should handle special characters in password")
    void testSpecialCharactersInPassword() {
        String rawPassword = "!@#$%^&*()_+-=[]{}|;':,.<>?";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    @DisplayName("Should handle unicode characters in password")
    void testUnicodeCharactersInPassword() {
        String rawPassword = "パスワード123密码";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    @DisplayName("Should handle whitespace in password")
    void testWhitespaceInPassword() {
        String rawPassword = "pass word with spaces";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    @DisplayName("Should handle null password gracefully")
    void testNullPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            passwordEncoder.encode(null);
        });
    }

    @Test
    @DisplayName("Should have consistent encoding strength")
    void testEncodingStrengthIsConsistent() {
        String rawPassword = "testPassword";
        String encoded1 = passwordEncoder.encode(rawPassword);
        String encoded2 = passwordEncoder.encode(rawPassword);
        
        // Both should have same length (BCrypt always produces 60 character hashes)
        assertEquals(encoded1.length(), encoded2.length());
        assertEquals(60, encoded1.length());
    }

    @Test
    @DisplayName("Should use BCrypt algorithm")
    void testUseBCryptAlgorithm() {
        String rawPassword = "testPassword";
        String encoded = passwordEncoder.encode(rawPassword);
        
        // BCrypt hashes start with $2a$, $2b$, $2x$, or $2y$
        assertTrue(encoded.startsWith("$2"));
    }

    // ==================== Bean Retrieval Tests ====================

    @Test
    @DisplayName("Should retrieve BCryptPasswordEncoder by class type")
    void testRetrievePasswordEncoderByType() {
        BCryptPasswordEncoder encoder = applicationContext.getBean(BCryptPasswordEncoder.class);
        assertNotNull(encoder);
        assertSame(passwordEncoder, encoder);
    }

    @Test
    @DisplayName("Should have only one BCryptPasswordEncoder bean")
    void testSinglePasswordEncoderBean() {
        String[] beanNames = applicationContext.getBeanNamesForType(BCryptPasswordEncoder.class);
        assertEquals(1, beanNames.length);
    }

    @Test
    @DisplayName("Should be same bean instance when retrieved multiple times")
    void testPasswordEncoderBeanIsSingleton() {
        BCryptPasswordEncoder encoder1 = applicationContext.getBean(BCryptPasswordEncoder.class);
        BCryptPasswordEncoder encoder2 = applicationContext.getBean(BCryptPasswordEncoder.class);
        
        assertSame(encoder1, encoder2);
    }

    // ==================== Integration Tests ====================

    @Test
    @DisplayName("Should encode and match password in sequence")
    void testEncodeAndMatchSequence() {
        String[] passwords = {"password1", "password2", "password3"};
        String[] encodedPasswords = new String[passwords.length];
        
        // Encode all passwords
        for (int i = 0; i < passwords.length; i++) {
            encodedPasswords[i] = passwordEncoder.encode(passwords[i]);
        }
        
        // Verify each password matches its hash
        for (int i = 0; i < passwords.length; i++) {
            assertTrue(passwordEncoder.matches(passwords[i], encodedPasswords[i]));
        }
        
        // Verify cross-matching fails
        for (int i = 0; i < passwords.length; i++) {
            for (int j = 0; j < passwords.length; j++) {
                if (i != j) {
                    assertFalse(passwordEncoder.matches(passwords[i], encodedPasswords[j]));
                }
            }
        }
    }

    @Test
    @DisplayName("Should work with common password patterns")
    void testCommonPasswordPatterns() {
        String[] commonPasswords = {
            "MyPassword123!",
            "admin@123",
            "user_pass_2024",
            "SecurePass#2024",
            "Test123@Pass"
        };
        
        for (String password : commonPasswords) {
            String encoded = passwordEncoder.encode(password);
            assertTrue(passwordEncoder.matches(password, encoded),
                    "Password encoding/matching failed for: " + password);
        }
    }

    @Test
    @DisplayName("Should verify passwordEncoder is not null after autowiring")
    void testPasswordEncoderAutowiring() {
        assertNotNull(passwordEncoder, "BCryptPasswordEncoder should be autowired");
    }
}
