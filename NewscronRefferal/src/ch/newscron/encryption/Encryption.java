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
import java.util.StringJoiner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.crypto.spec.IvParameterSpec;


public class Encryption {
    
    private static final byte[] key = "newscron12345678".getBytes(); //Key for AES algorithm - it has to be a multiple of 16bytes
    private static final String initializationVector = "AAAAAAAAAAAAAAAA"; //needed for AES algorithm
    
    private static Cipher cipher; //describe the algorithm used for encryption
    static {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {} 
    }
    
    /**
     * Given a JSONObject, it is encoded and returned as a String.
     * @param inviteData is a JSONObject having the data with the keys "custID", "rew1", "rew2" and "val"
     * @return encoded string 
     */
    public static String encode(JSONObject inviteData) {
        try {
            if(checkDataValidity(inviteData)) {     //NOTE: To obtain max number of available characters call the function availableParameterLength(String initialURL)
                //Create hash from inviteData fields
                byte[] hash = createMD5Hash(inviteData);

                return encode(inviteData, new String(hash,"UTF-8"));
            }
        } catch (Exception e) {}
        
        return null;
    }
  
    /**
     * Given a JSONObject, and maybe an hash, it is encoded and returned as a String.
     * @param inviteData is a JSONObject having the data with the keys "custID", "rew1", "rew2" and "val"
     * @param md5Hash is a String that substitute the hash computed using md5 algorithm, in case it is not null and not empty
     * @return encoded string 
     */
    protected static String encode(JSONObject inviteData, String md5Hash) {
        
        try {
            //Check in case we want to add a given hash (having at the end a corrupt data)
            if(md5Hash != null && !md5Hash.isEmpty()) {
                //Add md5Hash to JSONObject
                inviteData.put("hash", md5Hash);
            } else {
                return null;
            }

            //Encode inviteData (with hash) JSONObject
            String params = inviteData.toString();
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(initializationVector.getBytes("UTF-8")));
            return Base64.encodeBase64URLSafeString(cipher.doFinal(params.getBytes())); //to ensure valid URL characters
        } catch (Exception e) {}
        
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

                if (receivedHash.equals(new String(hashOfData,"UTF-8"))) { //Valid data
                    return receivedData.toString();
                }
            }
        } catch (Exception e) {} //Invalid data (including encryption algorithm exceptions
        
        return null;
    }
    
    /**
     * Given a JSONObject, extracts its fields and computes the hash using the MD5 algorithm
     * @param obj is a JSONObject consisting of the four fields
     * @return a MD5 hash of type byte[]
     */
    public static byte[] createMD5Hash(JSONObject obj) {
        //Create a string of the fields with format: "<custID>$<rew1>$<rew2>$<val>"
        StringJoiner stringToHash = new StringJoiner("$");
        stringToHash.add((String) obj.get("custID"));
        stringToHash.add((String) obj.get("rew1"));
        stringToHash.add((String) obj.get("rew2"));
        stringToHash.add((String) obj.get("val"));
        
        //Get hash of string  
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] hash = m.digest(stringToHash.toString().getBytes("UTF-8"));
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
                obj.get("custID") != null && !((String)obj.get("custID")).isEmpty() &&
                obj.get("rew1") != null && !((String)obj.get("rew1")).isEmpty() &&
                obj.get("rew2") != null && !((String)obj.get("rew2")).isEmpty() &&
                obj.get("val") != null && !((String)obj.get("val")).isEmpty();
    }
    
    
    
    /**
     * Computes the max number of available characters, considering the initial URL, base64, AES, MD5 hash and JSON object.
     * !! Available characters do not include the follow special characters (which require > 1 bytes): "/", "\", """, "'" and any kind of accents!!
     * @param initialURL ("http://domain/path")
     * @return the max number of available characters for the four fields (customerID, reward1, reward2, validity)
     */
    public static Integer availableParameterLength(String initialURL) {
        try {
            JSONObject emptyData = new JSONObject();
            emptyData.put("custID", "");
            emptyData.put("rew1", "");
            emptyData.put("rew2", "");
            emptyData.put("val", "");
            emptyData.put("hash", "");

            //Max size of URL for Internet Explorer... :-(
            int maxURLSize = 2000;

            //Remove size of initial url (host+domain)
            maxURLSize -= initialURL.getBytes("UTF-8").length;

            //Base64 ratio of 3/4
            maxURLSize = (int) Math.ceil((maxURLSize*3)/4);

            //Maximum number of blocks available * 16
            maxURLSize = (int) Math.floor(maxURLSize/16) * 16;

            //Remove size of hash (16 bytes) and empty JSON (without fields)
            maxURLSize -= (16 + emptyData.toJSONString().getBytes("UTF-8").length);

            //Remaining number of characters available
            return maxURLSize;

        } catch (Exception e) {};
        
        return null;
    }
    
}