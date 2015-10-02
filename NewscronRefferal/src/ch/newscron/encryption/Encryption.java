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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.crypto.spec.IvParameterSpec;


public class Encryption {
    
    private static final byte[] key = "newscron12345678".getBytes(); //Key for AES - multiple of 16bytes. Generate random?
    private static String iv = "AAAAAAAAAAAAAAAA";

    /**
     * 
     * @param JSONparams
     * @return encoded string
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.NoSuchAlgorithmException
     * @throws org.json.simple.parser.ParseException
     */
    
    public static String encode(JSONObject JSONparams) throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException {
        
        if(checkJSONObject(JSONparams)) {
            try {
                MessageDigest m = MessageDigest.getInstance("MD5");
                byte[] hashOfData = m.digest(JSONObjectToString(JSONparams).getBytes("UTF-8"));

                JSONObject j = JSONparams;
                j.put("hash", Arrays.toString(hashOfData));

                String params = JSONObjectToString(j);
            
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes("UTF-8")));
                return Base64.encodeBase64URLSafeString(cipher.doFinal(params.getBytes())); //to ensure valid URL characters
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NullPointerException e) {}
        }
        
        return "error";
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
            
            JSONParser parser = new JSONParser();
            JSONObject j = (JSONObject) parser.parse(result);
            String receivedHash = (String) j.get("hash");
            j.remove("hash");
            
            if(checkJSONObject(j)) {
                MessageDigest m = MessageDigest.getInstance("MD5");
                byte[] hashOfData = m.digest(JSONObjectToString(j).getBytes("UTF-8"));
                if (receivedHash.equals(Arrays.toString(hashOfData))) {
                    return JSONObjectToString(j);
                } else {
                    return "corrupt";
                }
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | ParseException | NullPointerException | IllegalArgumentException e) {}
        
        return "error";
    }
    
    public static String JSONObjectToString(JSONObject obj) {
        String JSONstringify;
        if(obj.get("hash") == null) {
            JSONstringify = "{\"" + 
                                "custID" + "\":\"" + obj.get("custID").toString() + "\",\"" +
                                "rew1" + "\":\"" + obj.get("rew1").toString() + "\",\"" +
                                "rew2" + "\":\"" + obj.get("rew2").toString() + "\",\"" +
                                "val" + "\":\"" + obj.get("val").toString() + "\"}";
        } else {
            JSONstringify = "{\"" + 
                                "custID" + "\":\"" + obj.get("custID").toString() + "\",\"" +
                                "rew1" + "\":\"" + obj.get("rew1").toString() + "\",\"" +
                                "rew2" + "\":\"" + obj.get("rew2").toString() + "\",\"" +
                                "val" + "\":\"" + obj.get("val").toString() + "\",\"" +
                                "hash" + "\":\"" + obj.get("hash").toString() + "\"}";
        }
        
        return JSONstringify;
    }
    
    public static boolean checkJSONObject(JSONObject obj) {
        return obj != null && obj.size() == 4 && obj.get("custID") != null && obj.get("rew1") != null && obj.get("rew2") != null && obj.get("val") != null;
    }
    
}