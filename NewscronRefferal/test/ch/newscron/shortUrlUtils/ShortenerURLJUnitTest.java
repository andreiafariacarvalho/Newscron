/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.shortUrlUtils;


import ch.newscron.encryption.Encryption;
import java.io.UnsupportedEncodingException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
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
        
        assertNull(ShortenerURL.getShortURL(""));
        
        assertNotNull(shortURL);
        
        assertFalse(longURL.equals(shortURL));
        
        assertTrue(shortURL.substring(0,14).equals("http://goo.gl/"));
                
    }
    
    @Test
    public void getURLJSONObjectTest() {
        assertNull(ShortenerURL.getURLJSONObject(null));

        ShortLinkStat shortURLJSONObject = ShortenerURL.getURLJSONObject(shortURL);
        
        assertTrue(shortURLJSONObject.shortUrl.equals(shortURL));
        
        assertTrue(shortURLJSONObject.longUrl.equals(longURL));
    }
    
    @Test
    public void getClicksShortURLTest() {
        
        //TODO: How to simulate a 'click' ? Or do so manually?
        
    }
    @Test
    public void getLongURLTest() {
        
        assertTrue(ShortenerURL.getLongURL(shortURL).equals(longURL));
    }
    
    @Test
    public void setDataTest() {
        //Create fake jsonNode for testing
        JSONObject analytics = new JSONObject();
        JSONObject twoHours = new JSONObject();
        twoHours.put("shortUrlClicks", 6);
        JSONObject day = new JSONObject();
        day.put("shortUrlClicks", 6);
        JSONObject week = new JSONObject();
        week.put("shortUrlClicks", 7);
        JSONObject month = new JSONObject();
        month.put("shortUrlClicks", 7);
        JSONObject allTime = new JSONObject();
        allTime.put("shortUrlClicks", 10);
        
        analytics.put("twoHours", twoHours);
        analytics.put("week", week);
        analytics.put("month", month);
        analytics.put("day", day);
        analytics.put("allTime", allTime);
        
        JSONObject node = new JSONObject();
        String shortUrl = "http:\\/\\/goo.gl\\/14Gmwu";
        String longUrl = "http:\\/\\/www.newscron.com\\/invite\\/abc";
        node.put("id", shortUrl);
        node.put("longUrl", longUrl);
        node.put("analytics", analytics);
        
        ObjectMapper mapper = new ObjectMapper(); 
        JsonNode n = mapper.convertValue(node, JsonNode.class);
        
        ShortLinkStat statsObj = ShortenerURL.setData(n);
        assertTrue(statsObj.longUrl.equals(longUrl));
        assertTrue(statsObj.shortUrl.equals(shortUrl));
        assertTrue(statsObj.allTimeShortClicks == 10);
        assertTrue(statsObj.monthShortClicks == 7);
        assertTrue(statsObj.weekShortClicks == 7);
        assertTrue(statsObj.dayShortClicks == 6);
        assertTrue(statsObj.twoHoursShortClicks == 6);

        
    }
}
