package com.wanted.preonboarding.cipher;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class Encryptor {
    private static final byte[] encryptionKeyString =  "thisisa128bitkey".getBytes(StandardCharsets.UTF_8);
    public String encryptMessage(byte[] message)
            throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(encryptionKeyString, "AES");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        return new String(cipher.doFinal(message));
    }
    public String decryptMessage(byte[] encryptedMessage)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(encryptionKeyString, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(encryptedMessage));
    }
}
