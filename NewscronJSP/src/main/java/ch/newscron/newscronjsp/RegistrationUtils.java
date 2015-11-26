package ch.newscron.newscronjsp;

import ch.newscron.encryption.CouponData;
import ch.newscron.encryption.Encryption;
import org.json.simple.parser.ParseException;
import ch.newscron.registration.UserRegistration;
import ch.newscron.referralUrlUtils.ShortenerURL;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private static final DateFormat valDateFormat = new SimpleDateFormat("dd.MM.yy"); // Format of the date used to store the validity

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
        String referralURL = ShortenerURL.getReferralURL(domain + "referral/" + longURL);
        UserRegistration.insertUser(firstName, lastName, emailAdd, referralURL);
    }
    
    public String checkURLValidity(String registrationURL) throws ParseException {
        String[] urlParts = registrationURL.split("/");
        if(urlParts.length > 4) {
            String encodedPart = urlParts[4];
            CouponData url = Encryption.decode(encodedPart.trim());

            if (url == null) {
                return "<p> Invalid URL </p>";
            } else { //url is not corrupt - check date validity
                return "<p> [URL valid until: " + valDateFormat.format(url.getVal()) + " ]</p>";
            }
        }
        return "<p> Validity expired </p>";
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
