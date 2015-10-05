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
import java.security.MessageDigest;
import java.util.Arrays;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.crypto.spec.IvParameterSpec;


public class Encryption {
    
    private static final byte[] key = "newscron12345678".getBytes(); //Key for AES algorithm - it has to be a multiple of 16bytes
    private static final String initializationVector = "AAAAAAAAAAAAAAAA"; //needed for AES algorithm

    /**
     * Given a JSONObject, it is encoded and returned as a String.
     * @param inviteData is a JSONObject having the data with the keys "custID", "rew1", "rew2" and "val"
     * @return encoded string 
     */
    public static String encode(JSONObject inviteData) {
        return encode(inviteData, null);
        
    }
  
    /**
     * Given a JSONObject, and maybe an hash, it is encoded and returned as a String.
     * @param inviteData is a JSONObject having the data with the keys "custID", "rew1", "rew2" and "val"
     * @param md5Hash is a String that substitute the hash computed using md5 algorithm, in case it is not null and not empty (it is needed to produce automatic data corrupt for tests)
     * @return encoded string 
     */
    protected static String encode(JSONObject inviteData, String md5Hash) {
        
        if(checkDataValidity(inviteData)) {
            try {
                //Check in case we want to add a given hash (having at the end a corrupt data)
                if(md5Hash != null && !md5Hash.equals("")) {
                    //Add md5Hash to JSONObject
                    inviteData.put("hash", md5Hash);
                } else {
                    //Create hash from inviteData fields
                    byte[] hash = createMD5Hash(inviteData);

                    //Add hash to JSONObject
                    inviteData.put("hash", Arrays.toString(hash));
                }
                
                //Encode inviteData (with hash) JSONObject
                String params = inviteData.toString();
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(initializationVector.getBytes("UTF-8")));
                return Base64.encodeBase64URLSafeString(cipher.doFinal(params.getBytes())); //to ensure valid URL characters
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        return null;
    }

    /**
     * Given a String, it is decoded and the result is returned as a String as well.
     * @param encodedUrl is a String that have the full data encrypted
     * @return decoded String
     */
    public static String decode(String encodedUrl) {
        
        try {
            
            //Decode URL
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(initializationVector.getBytes("UTF-8")));
            
            String result = new String(cipher.doFinal(Base64.decodeBase64(encodedUrl)));
            
            //Extract and remove hash from JSONObject
            JSONParser parser = new JSONParser();
            JSONObject receivedData = (JSONObject) parser.parse(result);
            String receivedHash = (String) receivedData.get("hash");
            receivedData.remove("hash");
            
            //Compare received hash with newly computed hash
            if(checkDataValidity(receivedData)) {
                byte[] hashOfData = createMD5Hash(receivedData);

                if (receivedHash.equals(Arrays.toString(hashOfData))) { //Valid data
                    return receivedData.toString();
                } else { //Corrupt data
                    return "";
                }
            }
        } catch (Exception e) { //Other errors (encryption algorithm)
            return e.getMessage();
        }
        
        //Invalid data
        throw new NullPointerException("Invalid data");
    }
    
    /**
     * Given a JSONObject, it is returned the same object in a String format.
     * @param obj is the JSONObject to stringify
     * @return JSONObject stringified
     */
//    public static String JSONObjectToString(JSONObject obj) {
//        if(obj != null) {
//            String JSONstringify;
//            if(obj.get("hash") == null) {
//                JSONstringify = obj.get("custID").toString() + "$" +
//                                obj.get("rew1").toString() + "$" +
//                                obj.get("rew2").toString() + "$" +
//                                obj.get("val").toString();
//            } else {
//                JSONstringify = "{\"" + 
//                                    "custID" + "\":\"" + obj.get("custID").toString() + "\",\"" +
//                                    "rew1" + "\":\"" + obj.get("rew1").toString() + "\",\"" +
//                                    "rew2" + "\":\"" + obj.get("rew2").toString() + "\",\"" +
//                                    "val" + "\":\"" + obj.get("val").toString() + "\",\"" +
//                                    "hash" + "\":\"" + obj.get("hash").toString() + "\"}";
//            }
//            return JSONstringify;
//        }
//        return "";
//    }
    
    /**
     * Given a JSONObject, extracts its fields and computes the hash using the MD5 algorithm
     * @param obj is a JSONObject consisting of the four fields
     * @return a MD5 hash of type byte[]
     */
    public static byte[] createMD5Hash(JSONObject obj) {
        //Create a string of the fields with format: "<custID>$<rew1>$<rew2>$<val>"
        String stringToHash = obj.get("custID").toString() + "$" +
                                obj.get("rew1").toString() + "$" +
                                obj.get("rew2").toString() + "$" +
                                obj.get("val").toString();
        //Get hash of string  
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] hash = m.digest(stringToHash.getBytes("UTF-8"));
            return hash;
        } catch (Exception e) {}
        
        return null;
    }
    
    /**
     * Given a JSONObject, it is checked if it is a good JSONObject depending what data we need.
     * @param obj is the JSONObject to check
     * @return true if the JSONObject is a good one, false otherwise
     */
    public static boolean checkDataValidity(JSONObject obj) {
        return obj != null && obj.size() == 4 && 
                obj.get("custID") != null && obj.get("custID") != "" &&
                obj.get("rew1") != null && obj.get("rew1") != "" &&
                obj.get("rew2") != null && obj.get("rew2") != "" &&
                obj.get("val") != null && obj.get("val") != "";
    }
    
}