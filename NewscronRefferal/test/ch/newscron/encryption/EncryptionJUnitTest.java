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

        JSONParser parser = new JSONParser();
        JSONObject decodedJSON = (JSONObject) parser.parse(stringDecoded);
        inviteData.remove("hash");
        assertTrue(compareJSONObjects(inviteData, decodedJSON));

        // With not appropriate string (invalid)
        String encodedWrong = "vKnxBLGBGdG3PIZlv4w8DpyQfvPhtz_7HayuA6b2-DC1M45RL7LcBc_p2IIXl4eXDphGxx5esQx74kE-txVij1KuqgW6J7pC2yCSIDxVfJkfHdubKRdvC7aFhclXq";
        assertNull(Encryption.decode(encodedWrong));

        // Hash of valid data
        byte[] hash = Encryption.createMD5Hash(inviteData);

        // Corrupt data
        JSONObject inviteData2 = new JSONObject();
        inviteData2.put("custID", "12345");
        inviteData2.put("rew1", "40%");
        inviteData2.put("rew2", "100%"); //!! Changed (in comparison to inviteData)
        inviteData2.put("val", "05/10/2015");


        //Send corrupt data (inviteData2) with valid hash of inviteData.
        //The hash of the corrupt data will differ from the hash of the valid data.
        String corruptEncodedURL = Encryption.encode(inviteData2, new String(hash,"UTF-8"));
        assertTrue(Encryption.decode(corruptEncodedURL) == null); //Indicating corrupt data

    }

    
    @Test
    public void checkDataValidityTest() throws ParseException {
        
        // With null JSONObject
        assertFalse(Encryption.checkDataValidity(null));

        // With good JSONObject
        assertTrue(Encryption.checkDataValidity(inviteData));

        // With not appropriate data
        inviteData.remove("custID");
        assertFalse(Encryption.checkDataValidity(inviteData));
        //Valid length - invalid fields
        inviteData = createNewInviteDataObject();
        inviteData.remove("rew1");
        inviteData.put("new", "data");
        assertFalse(Encryption.checkDataValidity(inviteData));
        //Invalid length
        inviteData = createNewInviteDataObject();
        inviteData.remove("rew2");
        assertFalse(Encryption.checkDataValidity(inviteData));
        //Invalid length
        inviteData = createNewInviteDataObject();
        inviteData.remove("val");
        assertFalse(Encryption.checkDataValidity(inviteData));
        //Invalid length
        inviteData = createNewInviteDataObject();
        inviteData.put("new", "data");
        assertFalse(Encryption.checkDataValidity(inviteData));
        //Empty key
        inviteData = createNewInviteDataObject();
        inviteData.remove("val");
        inviteData.put("val", "");
        assertFalse(Encryption.checkDataValidity(inviteData));
    }
    
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
    
    public JSONObject createNewInviteDataObject() {
        JSONObject testInviteData = new JSONObject();
        testInviteData.put("custID", "12345");
        testInviteData.put("rew1", "40%");
        testInviteData.put("rew2", "50%");
        testInviteData.put("val", "05/10/2015");
        return testInviteData;
    }
}
