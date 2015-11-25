package com.paynesoftware.qualitativehealthsystems;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Cryptography {

    private PrivateKey mPrivateKey;

    public static String encryptSHA256(String stringToEncrypt) {
        MessageDigest messageDigest;
        String encryptedString = null;
        char[] encryptedCharArray = null;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(stringToEncrypt.getBytes());
            byte[] encryptedData = messageDigest.digest();
            //encryptedCharArray = org.apache.commons.codec.binary.Hex.encodeHex(encryptedData);
            encryptedCharArray = android.util.Base64.encode(encryptedData, android.util.Base64.NO_WRAP).toString().toCharArray();
            encryptedString = new String(encryptedCharArray);

        } catch (NoSuchAlgorithmException e) {
            encryptedString = ourHash(stringToEncrypt);
        }

        return encryptedString;

    }

//	public static String encryptRSA2048(String stringToEncrypt) {
//		MessageDigest messageDigest;
//		String encryptedString = null;
//
//		return encryptedString;
//	}

    public byte[] RSAEncrypt(final String plain) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        mPrivateKey = kp.getPrivate();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plain.getBytes());
        System.out.println("EEncrypted?????" + android.util.Base64.encode(encryptedBytes, android.util.Base64.NO_WRAP).toString());

        return encryptedBytes;
    }

    public String RSADecrypt(final byte[] encryptedBytes) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher1 = Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE, mPrivateKey);
        byte[] decryptedBytes = cipher1.doFinal(encryptedBytes);
        String decrypted = new String(decryptedBytes);
        System.out.println("DDecrypted?????" + decrypted);
        return decrypted;
    }

    public static String ourHash(String inputString) {
        return String.valueOf(inputString.hashCode());
    }

}
