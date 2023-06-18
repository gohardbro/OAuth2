package com.example.oauth2.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JasyptTest extends JasyptConfig {

    @Test
    public void jasypt_encyrpt_decrypt_test() {
        // 암호화할 내용
        String plainText = "암호화할_내용";

        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword("키_암호");

        String encryptedText = jasypt.encrypt(plainText);
        String decryptedText = jasypt.decrypt(encryptedText);

        System.out.println("암호화된 내용: " + encryptedText);

        assertThat(plainText).isEqualTo(decryptedText);
    }
}