package ch.newscron.newscronjsp;

import ch.newscron.encryption.Encryption;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ch.newscron.registration.UserRegistration;
import ch.newscron.shortUrlUtils.ShortenerURL;

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
        String longURL = prevURL.split("/")[prevURL.split("/").length-1];
        String shortURL = ShortenerURL.getShortURL(domain + "invite/" + longURL);
        UserRegistration.insertUser(firstName, lastName, emailAdd, shortURL);
    }
    
    public String checkURLValidity(String registrationURL) throws ParseException {
        String encodedPart = registrationURL.split("/")[registrationURL.split("/").length-1];
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
            return "<p> URL valid until: " + val + " </p>";
        }
    }
}
