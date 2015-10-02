/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.newscronjsp;

import ch.newscron.encryption.Encryption;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Din
 */
public class ReadInviteData {
    
    
    
    String encodedURL;
    
    public ReadInviteData() {
    }

    public String parseURL() throws ParseException, Exception {
        String url = Encryption.decode(encodedURL.trim());; // TODO: change the way of keeping the url (String)
        switch (url) {
            case "error":
                return "<p> Invalid URL </p>";
            case "corrupt":
                return "<p> Corrupt URL - invalid data! </p>";
            default:
                JSONParser parser = new JSONParser();
                JSONObject newobj = (JSONObject) parser.parse(url);
                String custID = newobj.get("custID").toString();
                String rew1 = newobj.get("rew1").toString();
                String rew2 = newobj.get("rew2").toString();
                String val = newobj.get("val").toString();
                
                return "<table border='1'> "
                        + "<tr> " + " <td> custID: </td> <td>" + custID + "</td> " + "</tr> "
                        + "<tr> " + " <td> rew1: </td> <td>" + rew1 + "</td> " + "</tr> "
                        + "<tr> " + " <td> rew2: </td> <td>" + rew2 + "</td> " + "</tr> "
                        + "<tr> " + " <td> val: </td> <td>" + val + "</td> " + "</tr> "
                        + "</table>";
        }
    }
    public String getDataFromURL(String fullURL) {
        encodedURL = fullURL.split("/")[fullURL.split("/").length-1];
        return encodedURL;
    }
    
    public void setEncodedURL(String encodedURL) {
        this.encodedURL = encodedURL;
    }
    
    public String getEncodedURL() {
        return encodedURL;
    }
    
}
