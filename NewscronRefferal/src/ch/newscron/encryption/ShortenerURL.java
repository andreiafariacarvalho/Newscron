package ch.newscron.encryption;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.net.URL;
import org.apache.http.client.utils.URIBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
     * Given a long original URL, the function makes a request to the Google API (with key) and takes out the shortened goo.gl URL from the received JSONObject. 
     * @param longURL is the full (domain/path/ENCODED_DATA) URL created for inviting potential members
     * @return a String which is the goo.gl shortened URL correlated to the original long URL.
     */
    public static String getShortURL(String longURL) {
        try {
            JSONObject data = new JSONObject();
            data.put("longUrl", longURL);
            
            // HTTP request with the result
            HttpResponse<JsonNode> postResponse = Unirest.post(google_url).header("Content-Type", "application/json").body(data.toJSONString()).asJson();
            
            return (String) postResponse.getBody().getObject().get("id"); //If everything is working, then return the shortener URL

        } catch (Exception e) {}
        
        return null; //In case of errors or problems, just return the full and long URL
    }

    /**
     * Given a shortened goo.gl URL, the function makes a request to the Google API (with key) and receives information as a response in JSONObject format. 
     * @param shortURL is a String representing the shortened goo.gl URL
     * @return a JSONObject consisting of all the information about the URL (including statistics)
     */
    public static JSONObject getURLJSONObject(String shortURL) {
        try {
            // Creating the URL with Google API
            URIBuilder urlData = new URIBuilder(google_url);
            urlData.addParameter("shortUrl", shortURL);
            urlData.addParameter("projection", "FULL");
            URL url = urlData.build().toURL();
            
            // Get the JSONObject format of shortURL as String
            ObjectMapper mapper = new ObjectMapper();
            org.codehaus.jackson.JsonNode rootNode = mapper.readTree(url);
            if(rootNode == null) {
                return null;
            }
            
            // Convert String response to JSONObject
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(rootNode.toString());
            
        } catch(Exception e) {}
        
        return null;
    }
    
    /**
     * Given a shortened goo.gl URL, extracts the number of clicks from the analytics field of the JSONObject
     * @param shortURL is a String representing the shortened goo.gl URL
     * @return an int representing the number of all-time clicks of the shortened goo.gl URL
     */
    public static int getClicksShortURL(String shortURL) {
        JSONObject objectURL = getURLJSONObject(shortURL);
        return new Integer((String)((JSONObject)((JSONObject)objectURL.get("analytics")).get("allTime")).get("shortUrlClicks"));
    }
    
    /**
     * Given a shortened goo.gl URL, extracts the original long URL of the JSONObject
     * @param shortURL is a String representing the shortened goo.gl URL
     * @return a String representing the original URL
     */
    public static String getLongURL(String shortURL) {
        JSONObject objectURL = getURLJSONObject(shortURL);
        return (String) objectURL.get("longUrl");
    }

    
}