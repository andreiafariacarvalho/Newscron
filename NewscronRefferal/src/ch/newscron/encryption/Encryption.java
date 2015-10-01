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


import java.io.UnsupportedEncodingException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.crypto.spec.IvParameterSpec;


public class Encryption {
    
    private static final byte[] key = "newscron12345678".getBytes(); //Key for AES - multiple of 16bytes. Generate random?
    private static String iv = "AAAAAAAAAAAAAAAA";

    /**
     * 
     * @param params String that includes id, rew1, rew2, validity
     * @return encoded string
     */
    
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException {        
        String JSON = "{\"val\":\"10.10.10\",\"rew1\":\"10\",\"rew2\":\"20%\",\"custID\":\"123456\"}";
        
        String encodedResult = encode(JSON);
        System.out.println("EncodedRes: " + encodedResult);
        
        String decodedResult = decode(encodedResult);
        System.out.println("DecodedRes: " + decodedResult);
        
    }
    public static String encode(String params) throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException {
        
        MessageDigest m = MessageDigest.getInstance("MD5");
        byte[] hashOfData = m.digest(params.getBytes("UTF-8"));

        JSONParser parser = new JSONParser();
        JSONObject j = (JSONObject) parser.parse(params);
        j.put("hash", Arrays.toString(hashOfData));
        
        params = j.toString();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes("UTF-8")));
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
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes("UTF-8")));
            
            String result = new String(cipher.doFinal(Base64.decodeBase64(encodedUrl)));
            System.out.println("res: " + result);
            
            JSONParser parser = new JSONParser();
            JSONObject j = (JSONObject) parser.parse(result);
            String receivedHash = (String) j.get("hash");
            j.remove("hash");
            
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] hashOfData = m.digest(j.toString().getBytes("UTF-8"));
            
            
            if (receivedHash.equals(Arrays.toString(hashOfData))) {
                System.out.println("VALID");
            }
            
            else {
                System.out.println("HACKED");
            }
            
            return result;
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        return null;
    }
}
