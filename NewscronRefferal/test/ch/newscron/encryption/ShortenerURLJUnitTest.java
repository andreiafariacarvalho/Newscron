/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.encryption;

import java.io.UnsupportedEncodingException;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Din
 */
public class ShortenerURLJUnitTest {
    JSONObject inviteData = new JSONObject();
    String longURL;
    String shortURL;
    
    public ShortenerURLJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws UnsupportedEncodingException {
        inviteData.put("custID", "12345");
        inviteData.put("rew1", "40%");
        inviteData.put("rew2", "50%");
        inviteData.put("val", "05/10/2015");
        String stringEncoded = Encryption.encode(inviteData);
        
        longURL = "http://localhost:8080/invite/" + stringEncoded;
        shortURL = ShortenerURL.getShortURL(longURL);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void getShortURLTest() {
        
        assertTrue(ShortenerURL.getShortURL("").equals(""));
        
        assertNotNull(shortURL);
        assertFalse(longURL.equals(shortURL));
        
        assertTrue(shortURL.substring(0,14).equals("http://goo.gl/"));
        
        //Test with HTTP requests/responses ?
        
    }
    
    @Test
    public void getURLJSONObjectTest() {
        assertNull(ShortenerURL.getURLJSONObject(null));
        
        JSONObject shortURLJSONObject = ShortenerURL.getURLJSONObject(shortURL);
        
        assertTrue(shortURLJSONObject.get("id").equals(shortURL));
        
        assertTrue(shortURLJSONObject.get("longUrl").equals(longURL));

        assertTrue(shortURLJSONObject.get("kind").equals("urlshortener#url"));
        
        assertNotNull(shortURLJSONObject.get("analytics"));
    }
    
    @Test
    public void getClicksShortURLTest() {
        
        //TODO: How to simulate a 'click' ? Or do so manually?
        
    }
    @Test
    public void getLongURLTest() {
        
        assertTrue(ShortenerURL.getLongURL(shortURL).equals(longURL));
    }
}
