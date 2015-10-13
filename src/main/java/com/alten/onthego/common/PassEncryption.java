
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.common;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import org.apache.tomcat.util.codec.binary.Base64;

public class PassEncryption {

    private static byte[] sharedvector = {
        0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11
    };
    /* public static void main(String[] args) throws FileNotFoundException, IOException {
     BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\ka3146\\Documents\\GitHub\\OnTheGo\\src\\main\\java\\com\\alten\\onthego\\common\\picode.txt"));
     try {
     StringBuilder sb = new StringBuilder();
     String line = br.readLine();

     while (line != null) {
     sb.append(line);
     sb.append(System.lineSeparator());
     line = br.readLine();
     System.out.println(EncryptText(line));    
     }
     } finally {
     br.close();
     }
     }*/

    public String EncryptText(String RawText) {
        String EncText = "";
        byte[] keyArray = new byte[24];
        byte[] temporaryKey;
        String key = "developersnotedotcom";
        byte[] toEncryptArray = null;

        try {

            toEncryptArray = RawText.getBytes("UTF-8");
            MessageDigest m = MessageDigest.getInstance("MD5");
            temporaryKey = m.digest(key.getBytes("UTF-8"));

            if (temporaryKey.length < 24) // DESede require 24 byte length key
            {
                int index = 0;
                for (int i = temporaryKey.length; i < 24; i++) {
                    keyArray[i] = temporaryKey[index];
                }
            }

            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
            byte[] encrypted = c.doFinal(toEncryptArray);
            EncText = Base64.encodeBase64String(encrypted);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException NoEx) {
            JOptionPane.showMessageDialog(null, NoEx);
        }

        return EncText;
    }

    public String DecryptText(String EncText) throws IllegalBlockSizeException, BadPaddingException {

        String RawText = "";
        byte[] keyArray = new byte[24];
        byte[] temporaryKey;
        String key = "developersnotedotcom";
        byte[] toEncryptArray = null;

        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            temporaryKey = m.digest(key.getBytes("UTF-8"));

            if (temporaryKey.length < 24) // DESede require 24 byte length key
            {
                int index = 0;
                for (int i = temporaryKey.length; i < 24; i++) {
                    keyArray[i] = temporaryKey[index];
                }
            }

            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
            byte[] decrypted = c.doFinal(Base64.decodeBase64(EncText));

            RawText = new String(decrypted, "UTF-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException NoEx) {
            JOptionPane.showMessageDialog(null, NoEx);
        }

        return RawText;

    }

}
