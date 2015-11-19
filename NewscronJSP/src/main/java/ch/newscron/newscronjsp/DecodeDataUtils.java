/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.newscronjsp;

import ch.newscron.encryption.Encryption;
import ch.newscron.shortUrlUtils.ShortenerURL;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Din
 */
public class DecodeDataUtils {
    
    private String customerId;
    String encodedURL;
    public static String domain = "http://localhost:8080/";
    
    public DecodeDataUtils() {
    }

    public String showURLData() throws ParseException, Exception {
        String url = Encryption.decode(encodedURL.trim());

        if (url == null) {
                return "<p> Invalid URL </p>";
        }
        
        else if (url.equals("")) {
                return "<p> Corrupt URL - invalid data! </p>";
        }
        else {
            JSONParser parser = new JSONParser();
            JSONObject newobj = (JSONObject) parser.parse(url);
            customerId = newobj.get("custID").toString();
            String rew1 = newobj.get("rew1").toString();
            String rew2 = newobj.get("rew2").toString();
            String val = newobj.get("val").toString();

            return "<table border='0' class=\"center\"> "
                    + "<tr> " + " <td> custID: </td> <td>" + customerId + "</td> " + "</tr> "
                    + "<tr> " + " <td> rew1: </td> <td>" + rew1 + "</td> " + "</tr> "
                    + "<tr> " + " <td> rew2: </td> <td>" + rew2 + "</td> " + "</tr> "
                    + "<tr> " + " <td> val: </td> <td>" + val + "</td> " + "</tr> "
                    + "</table>";
        }
    }
    
    public void setUrlString(String fullURL) {
        encodedURL = fullURL.split("/")[fullURL.split("/").length-1];
    }
    
    public String getShorterUrl() throws IOException {
        return ShortenerURL.getShortURL(domain + "invite/" + encodedURL);
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
}
