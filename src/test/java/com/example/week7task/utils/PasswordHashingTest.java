package com.example.week7task.utils;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PasswordHashingTest {
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @Test
//    void encryptPassword() {
//    }
//
//    @Test
//    void decryptPassword() {
//    }
//}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PasswordHashingTest {
    String passwordOne = null;
    String passwordTwo = null;

    @BeforeEach
    void setup(){
        passwordOne =  "testing123";
        passwordTwo = "testing456";
    }

    @Test
    void testPasswordEncryption(){
        String encrypt = PasswordHashing.encryptPassword(passwordOne);
        String sameEncrypt = PasswordHashing.encryptPassword("testing123");
        String diffEncrypt = PasswordHashing.encryptPassword("testing");
        String pass2Encrypt = PasswordHashing.encryptPassword(passwordTwo);

        assertEquals(sameEncrypt, encrypt);
        assertNotEquals(diffEncrypt, encrypt);
        assertNotEquals(pass2Encrypt, encrypt);
    }

    @Test
    void testPasswordDecryption(){
        String decrypt = PasswordHashing.encryptPassword(passwordTwo);
        String sameEncrypt = PasswordHashing.encryptPassword("testing456");
        String diffEncrypt = PasswordHashing.encryptPassword("testing");
        String pass2Encrypt = PasswordHashing.encryptPassword(passwordOne);

        assertEquals(sameEncrypt, decrypt);
        assertNotEquals(diffEncrypt, decrypt);
        assertNotEquals(pass2Encrypt, decrypt);
    }
}