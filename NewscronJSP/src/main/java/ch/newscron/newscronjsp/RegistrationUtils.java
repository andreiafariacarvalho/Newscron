package ch.newscron.newscronjsp;

import ch.newscron.encryption.Encryption;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    
    
    public String checkURLValidity(String registrationURL) throws ParseException {
        System.out.println("registrationURL : " + registrationURL);
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
