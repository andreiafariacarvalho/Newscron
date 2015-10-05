/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.encryption;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andreiafariacarvalho
 */
public class EncryptionJUnitTest {
    JSONObject inviteData = new JSONObject();

    public EncryptionJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        inviteData.put("custID", "12345");
        inviteData.put("rew1", "40%");
        inviteData.put("rew2", "50%");
        inviteData.put("val", "05/10/2015");
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void encodeTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException {
        
    //With null string
    assertNull(Encryption.encode(null));
        
    // With working string
    String stringEncoded = Encryption.encode(inviteData);
    assertNotNull(stringEncoded);
    assertFalse(inviteData.toString().equals(stringEncoded));
    assertTrue(inviteData.toString().length() < stringEncoded.length());

    // With not appropriate string
    inviteData.put("extra", "value");
    assertNull(Encryption.encode(inviteData));
   
    inviteData.remove("extra");
    inviteData.remove("val");
    assertNull(Encryption.encode(inviteData));

    }
    
    @Test
    public void decodeTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException {
        
    // With null string
    assertNull(Encryption.decode(null));
        
    // With working string
    String stringEncoded = Encryption.encode(inviteData);
    String stringDecoded = Encryption.decode(stringEncoded);
    assertNotNull(stringDecoded);
//        JSONObject jString = (JSONObject) parser.parse(string);
//        JSONObject jStringDecoded = (JSONObject) parser.parse(stringDecoded);
//        assertTrue(jString.get("custID").equals(jStringDecoded.get("custID")));
//        assertTrue(jString.get("rew1").equals(jStringDecoded.get("rew1")));
//        assertTrue(jString.get("rew2").equals(jStringDecoded.get("rew2")));
//        assertTrue(jString.get("val").equals(jStringDecoded.get("val")));
//        assertTrue(jString.size() == jStringDecoded.size());
//        
//        // With not appropriate string
//        String encodedWrong = "vKnxBLGBGdG3PIZlv4w8DpyQfvPhtz_7HayuA6b2-DC1M45RL7LcBc_p2IIXl4eXDphGxx5esQx74kE-txVij1KuqgW6J7pC2yCSIDxVfJkfHdubKRdvC7aFhclXq";
//        stringDecoded = Encryption.decode(encodedWrong);
//        assertTrue(stringDecoded.equals("error"));
//        
//        // * Right data...
//        // JSON: {"val":"02.11.2015","custID":"12345","rew1":"40%","rew2":"50%"}
//        // hash: [-126, 124, 75, -123, -85, -109, 100, 18, 108, 100, 21, 21, 23, -15, 67, -20]
//        // Url decoded: vKnxBLGBGdG3PIibiZlv4w8DpyQfvPhtz_7HayuA6b2LGEqPkG8rPTzxgOS0syx7_GyBoapgPTY8mBNsyC7ogDDfZBtH73rLc8td8LDMv1rDul3xRq4tqLst0969TPc04paX-YyHEynrtb5LNe7cKpjNQYYLtRV2VhkTeejFjj9DneOwTstoMueuhSlStR3WJiaX9r_5tdiobjzW7Zn1VQ
//        // * Corrupt data...
//        // JSON: {"val":"02.11.2015","custID":"12345","rew1":"40%","rew2":"100%"}
//        // hash: [-126, 124, 75, -123, -85, -109, 100, 18, 108, 100, 21, 21, 23, -15, 67, -20] which is the same as "Right data"
//        // Url corrupt: vKnxBLGBGdG3PIibiZlv4w8DpyQfvPhtz_7HayuA6b2-DC1M45RL7LcBc_p2IIXl4eXDphGxx5esQx74kE-txVij1KuqgW6J7pC2yCSIDxVfJkfHdubKRdvC7aFhclXq
//        encodedWrong = "vKnxBLGBGdG3PIibiZlv4w8DpyQfvPhtz_7HayuA6b2-DC1M45RL7LcBc_p2IIXl4eXDphGxx5esQx74kE-txVij1KuqgW6J7pC2yCSIDxVfJkfHdubKRdvC7aFhclXq";
//        stringDecoded = Encryption.decode(encodedWrong);
//        assertTrue(stringDecoded.equals("corrupt"));
    }
//    
//    @Test
//    public void JSONObjectToStringTest() throws ParseException {
//        
//        // Without hash
//        String string = "{\"custID\":\"123456\",\"rew1\":\"10\",\"rew2\":\"20%\",\"val\":\"10.10.10\"}";
//        JSONParser parser = new JSONParser();
//        JSONObject j = (JSONObject) parser.parse(string);
//        assertTrue(string.equals(JSONObjectToString(j)));
//        
//        // With hash
//        string = "{\"custID\":\"123456\",\"rew1\":\"10\",\"rew2\":\"20%\",\"val\":\"10.10.10\",\"hash\":\"mnvdnkfv\"}";
//        j = (JSONObject) parser.parse(string);
//        assertTrue(string.equals(JSONObjectToString(j)));
//        
//        // With null hash
//        assertTrue("".equals(JSONObjectToString(null)));
//    }
//    
//    @Test
//    public void checkJSONObjectTest() throws ParseException {
//        
//        // With null JSONObject
//        assertFalse(checkJSONObject(null));
//        
//        // With good JSONObject
//        String string = "{\"val\":\"10.10.10\",\"rew1\":\"10\",\"rew2\":\"20%\",\"custID\":\"123456\"}";
//        JSONParser parser = new JSONParser();
//        JSONObject obj = (JSONObject) parser.parse(string);
//        assertTrue(checkJSONObject(obj));
//        
//        // With not appropriate data
//        obj = (JSONObject) parser.parse(string);
//        obj.remove("custID");
//        assertFalse(checkJSONObject(obj));
//        obj = (JSONObject) parser.parse(string);
//        obj.remove("rew1");
//        obj.put("new", "data");
//        assertFalse(checkJSONObject(obj));
//        obj = (JSONObject) parser.parse(string);
//        obj.remove("rew2");
//        assertFalse(checkJSONObject(obj));
//        obj = (JSONObject) parser.parse(string);
//        obj.remove("val");
//        assertFalse(checkJSONObject(obj));
//        obj.put("new", "data");
//        assertFalse(checkJSONObject(obj));
//    }
    
    public boolean compareJSONObjects(JSONObject one, JSONObject two) {
        if (one.keySet().size() != two.keySet().size()) {
            return false;
        }
        Iterator<String> keysOne = one.keySet().iterator();
        while (keysOne.hasNext()) {
            String key = (String) keysOne.next();
            if (two.get(key) == null || !one.get(key).equals(two.get(key))) {
                return false;
            }            
        }
        return true;
    }
}
