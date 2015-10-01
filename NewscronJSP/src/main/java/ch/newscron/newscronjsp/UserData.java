/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.newscronjsp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ch.newscron.encryption.Encryption;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Din
 */

public class UserData {

    String custID;
    String rew1;
    String rew2;
    String val;
    String urlEncoded;

    public UserData() {
    }
    public void setCustID( String value )
    {
        custID = value;
    }

    public void setRew1( String value )
    {
        rew1 = value;
    }

    public void setRew2( String value )
    {
        rew2 = value;
    }

    public void setVal( String value )
    {
        val = value;
    }
    public String getCustID() { return custID; }

    public String getRew1() { return rew1; }

    public String getRew2() { return rew2; }
    
    public String getVal() { return val; }

    
    public JSONObject createJSON(String custID, String rew1, String rew2, String val) {
        JSONObject obj = new JSONObject();
        obj.put("custID", custID);
        obj.put("rew1", rew1);
        obj.put("rew2", rew2);
        obj.put("val", val);
        return obj;
    }
    public void setURLtoEncode() throws Exception {
        JSONObject fullParam = createJSON(custID, rew1, rew2, val);
        urlEncoded = Encryption.encode(fullParam.toString());
    }
    
    
    public String getURLtoEncode() {
        return urlEncoded;
    }
    
    public String getURLDecoded() throws Exception {
        return Encryption.decode(urlEncoded.trim());
    }
    
    public String getNumberBytes() {
        byte[] encoded = urlEncoded.getBytes();
        return new String("" + encoded.length);
    }
    
    public String getFullURL () {
        return "http://localhost:8080/invite/" + urlEncoded;
    }
    
    

}
