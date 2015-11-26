package ch.newscron.newscronjsp;

import ch.newscron.encryption.Encryption;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ch.newscron.registration.UserRegistration;
import ch.newscron.shortUrlUtils.ShortenerURL;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Din
 */
public class RegistrationUtils {
    public static String domain = "http://localhost:8080/";

    String firstName;
    String lastName;
    String emailAdd;
    
    public RegistrationUtils() {
    }
    
    public void setFirstName(String value) {
        firstName = value;
    }
    
    public void setLastName(String value) {
        lastName = value;
    }
    
    public void setEmailAdd(String value) {
        emailAdd = value;
    }
    
    public void insertUser(String prevURL) {
        String longURL = prevURL.split("/")[4];
        String shortURL = ShortenerURL.getShortURL(domain + "referral/" + longURL);
        UserRegistration.insertUser(firstName, lastName, emailAdd, shortURL);
    }
    
    public String checkURLValidity(String registrationURL) throws ParseException {
        String encodedPart = registrationURL.split("/")[4];
        String url = Encryption.decode(encodedPart.trim());

        if (url == null) {
            return "<p> Invalid URL </p>";
        }
        
        else if (url.equals("")) {
            return "<p> Corrupt URL - invalid data! </p>";
        }
        
        else { //url is not corrupt - check date validity
            JSONParser parser = new JSONParser();
            JSONObject newobj = (JSONObject) parser.parse(url);
            String val = newobj.get("val").toString();
            return "<p> [URL valid until: " + val + " ]</p>";
        }
    }
    
    public String getReward(String prevURL) {
        String[] urlParts = prevURL.split("/"); 
        if(urlParts.length >= 6) {
            try {
                return new String(Base64.decodeBase64((urlParts[5]).getBytes()),"UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(RegistrationUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return " - ";
    }
    
    public String getEncodedData(String prevURL) {
        String[] urlParts = prevURL.split("/"); 
        if(urlParts.length >= 5) {
            return urlParts[4];
        }
        return null;
    }
}
