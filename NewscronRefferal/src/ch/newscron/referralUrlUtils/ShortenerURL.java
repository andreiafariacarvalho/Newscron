package ch.newscron.referralUrlUtils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.net.URL;
import org.apache.http.client.utils.URIBuilder;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andreiafariacarvalho
 */
public class ShortenerURL {

    protected static final String GOOGLE_SHORTENER_URL = "https://www.googleapis.com/urlshortener/v1/url?key=%1s";
    protected static final String GOOGLE_SHORTENER_URL_KEY = "AIzaSyDwu91R6A4EhN-NeAYWrEqecSIn_z-3tmA";
    static String google_url = String.format(GOOGLE_SHORTENER_URL, GOOGLE_SHORTENER_URL_KEY);
       
    /**
     * Given a long original URL, the function makes a request to the Google API (with key) and takes out the shortened goo.gl URL from the received JsonNode. 
     * @param longURL is the full (domain/path/ENCODED_DATA) URL created for inviting potential members
     * @return a String which is the goo.gl shortened URL correlated to the original long URL.
     */
    public static String getReferralURL(String longURL) {
        try {
            JSONObject data = new JSONObject();
            data.put("longUrl", longURL);
            
            // HTTP request with the result
            HttpResponse<com.mashape.unirest.http.JsonNode> postResponse = Unirest.post(google_url).header("Content-Type", "application/json").body(data.toJSONString()).asJson();
            return (String) postResponse.getBody().getObject().get("id"); //If everything is working, then return the shortened URL

        } catch (Exception e) {}
        
        return null; //In case of errors or problems, just return the full and long URL
    }

    /**
     * Given a shortened goo.gl URL, the function makes a request to the Google API (with key), receives information as a response in JSONObject format and stores data into a ShortLinkStat object.
     * @param referralURL is a String representing the shortened goo.gl URL
     * @return a ShortLinkStat object consisting of the most important information (clicks, long and short URL)
     */
    public static ShortLinkStat getURLStatistics(String referralURL) {
        ShortLinkStat linkStat;

        try {
            // Creating the URL with Google API
            URIBuilder urlData = new URIBuilder(google_url);
            urlData.addParameter("shortUrl", referralURL);
            urlData.addParameter("projection", "FULL");
            URL url = urlData.build().toURL();
            
            // Get the JSONObject format of referralURL as String
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(url);
            if(rootNode == null) {
                return null;
            }
            
            linkStat = setData(rootNode);
            return linkStat;
            
        } catch(Exception e) {}
        
        return null;
    }
    
    /**
     * Given a shortened goo.gl URL, extracts the all-time number of clicks from a ShortLinkStat object
     * @param referralURL is a String representing the shortened goo.gl URL
     * @return an int representing the number of all-time clicks of the shortened goo.gl URL
     */
    public static int getClicksReferralURL(String referralURL) {
        return getURLStatistics(referralURL).allTimeShortClicks;
    }
    
    /**
     * Given a shortened goo.gl URL, extracts the original long URL from a ShortLinkStat object
     * @param referralURL is a String representing the shortened goo.gl URL
     * @return a String representing the original URL
     */
    public static String getLongURL(String referralURL) {
        return getURLStatistics(referralURL).longUrl;
    }

    /**
     * Given a JsonNode, fill all fields in a new object for holding these statistics
     * @param linkData JsonNode holding all short url data received from google API
     * @return a ShortLinkStat object having statistics
     */
    protected static ShortLinkStat setData(JsonNode linkData) {
        ShortLinkStat linkStat = new ShortLinkStat();
        
        try {
            JsonNode referralUrlNode = linkData.get("id");
            if (referralUrlNode != null) {
                linkStat.referralUrl = referralUrlNode.asText();
            }

            JsonNode longUrlNode = linkData.get("longUrl");
            if (longUrlNode != null) {
                linkStat.longUrl = longUrlNode.asText();
            }

            //Save clicks for all 5 different periods
            JsonNode analyticsNode = linkData.get("analytics");
            if (analyticsNode != null) {

                JsonNode allTimeNode = analyticsNode.get("allTime");
                if (allTimeNode != null) {
                    linkStat.allTimeShortClicks = allTimeNode.get("shortUrlClicks").asInt();
                }

                JsonNode monthNode = analyticsNode.get("month");
                if (monthNode != null) {
                    linkStat.monthShortClicks = monthNode.get("shortUrlClicks").asInt();
                }

                JsonNode weekNode = analyticsNode.get("week");
                if (weekNode != null) {
                    linkStat.weekShortClicks = weekNode.get("shortUrlClicks").asInt();
                }

                JsonNode dayNode = analyticsNode.get("day");
                if (dayNode != null) {
                    linkStat.dayShortClicks = dayNode.get("shortUrlClicks").asInt();
                }

                JsonNode twoHoursNode = analyticsNode.get("twoHours");
                if (twoHoursNode != null) {
                    linkStat.twoHoursShortClicks = twoHoursNode.get("shortUrlClicks").asInt();
                }
            }
        } catch(Exception e) {}
        
        return linkStat;
    }
  
}