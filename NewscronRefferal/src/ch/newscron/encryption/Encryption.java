/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.encryption;

/**
 *
 * @author andreiafariacarvalho + dintamari
 */


import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;

public class Encryption {
    
    private static final byte[] key = "newscron12345678".getBytes(); //Key for AES - multiple of 16bytes. Generate random?
    
    /**
     * 
     * @param params String that includes id, rew1, rew2, validity
     * @return encoded string
     */
    public static String encode(String params) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.encodeBase64URLSafeString(cipher.doFinal(params.getBytes())); //to ensure valid URL characters
        }
        catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @param encodedUrl 
     * @return decoded String
     */
    public static String decode(String encodedUrl) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.decodeBase64(encodedUrl)));
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        return null;
    }
}
