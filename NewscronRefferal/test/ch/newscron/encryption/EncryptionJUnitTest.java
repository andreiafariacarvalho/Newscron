/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.encryption;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONObject;
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
    DateFormat valDateFormat = new SimpleDateFormat("dd.MM.yy");

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
        inviteData.put("userId", "12345");
        inviteData.put("rew1", "40%");
        inviteData.put("rew2", "50%");
        inviteData.put("val", "05.10.2015");
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void encodeTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException, java.text.ParseException {
        
        //With null string
        assertNull(Encryption.encode(0, null, null, null));
        assertNull(Encryption.encode(0, null, null, new Date()));

        long userIdInvite = Long.parseLong((String)inviteData.get("userId"));
        String rew1Invite = (String)inviteData.get("rew1");
        String rew2Invite = (String)inviteData.get("rew2");
        Date valInvite = valDateFormat.parse((String)inviteData.get("val"));
        
        // With working string
        String stringEncoded = Encryption.encode(userIdInvite, rew1Invite, rew2Invite, valInvite);
        assertNotNull(stringEncoded);
        assertFalse(inviteData.toString().equals(stringEncoded));
        assertTrue(inviteData.toString().length() < stringEncoded.length());

    }
    
    @Test
    public void decodeTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException, java.text.ParseException {
        
        // With null string
        assertNull(Encryption.decode(null));

        long userIdInvite = Long.parseLong((String)inviteData.get("userId"));
        String rew1Invite = (String)inviteData.get("rew1");
        String rew2Invite = (String)inviteData.get("rew2");
        Date valInvite = valDateFormat.parse((String)inviteData.get("val"));
        
        // With working string
        String stringEncoded = Encryption.encode(userIdInvite, rew1Invite, rew2Invite, valInvite);
        CouponData stringDecoded = Encryption.decode(stringEncoded);
        assertNotNull(stringDecoded);

        inviteData.remove("hash");
        assertTrue(compareCouponData(inviteData, stringDecoded));

        // With not appropriate string (invalid)
        String encodedWrong = "vKnxBLGBGdG3PIZlv4w8DpyQfvPhtz_7HayuA6b2-DC1M45RL7LcBc_p2IIXl4eXDphGxx5esQx74kE-txVij1KuqgW6J7pC2yCSIDxVfJkfHdubKRdvC7aFhclXq";
        assertNull(Encryption.decode(encodedWrong));

        // Hash of valid data
        byte[] hash = Encryption.createMD5Hash(inviteData);

        // Corrupt data
        JSONObject inviteData2 = new JSONObject();
        inviteData2.put("userId", "12345");
        inviteData2.put("rew1", "40%");
        inviteData2.put("rew2", "100%"); //!! Changed (in comparison to inviteData)
        inviteData2.put("val", "05.10.2015");

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
        inviteData.remove("userId");
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
    
    private boolean compareCouponData(JSONObject one, CouponData two) throws java.text.ParseException {
        return two.getUserId() == Long.parseLong((String) one.get("userId")) &&
                two.getRew1().equals(one.get("rew1")) &&
                two.getRew2().equals(one.get("rew2")) &&
                two.getVal().equals(valDateFormat.parse((String)one.get("val")));
    }
    
    public JSONObject createNewInviteDataObject() {
        JSONObject testInviteData = new JSONObject();
        testInviteData.put("userId", "12345");
        testInviteData.put("rew1", "40%");
        testInviteData.put("rew2", "50%");
        testInviteData.put("val", "05.10.2015");
        return testInviteData;
    }
}
