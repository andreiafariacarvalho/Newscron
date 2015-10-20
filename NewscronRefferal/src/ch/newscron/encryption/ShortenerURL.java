package ch.newscron.encryption;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
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

    public static final String GOOGLE_SHORTENER_URL = "https://www.googleapis.com/urlshortener/v1/url";
    public static final String GOOGLE_SHORTENER_URL_KEY = "?key=AIzaSyDwu91R6A4EhN-NeAYWrEqecSIn_z-3tmA";
    public static final String GOOGLE_SHORTENER_URL_OPTION_shortURL = "&shortUrl=";
    public static final String GOOGLE_SHORTENER_URL_OPTION_projection = "&projection=FULL";
    
    /**
     * 
     * @param longURL
     * @return 
     */
    public static String getShortenerURL(String longURL) {
        String data = "{\"longUrl\": \"" + longURL + "\"}";
        try {
            //Creation of one HTTP connection
            URL url = new URL(GOOGLE_SHORTENER_URL + GOOGLE_SHORTENER_URL_KEY);
            
            //Preparing the HTTP request
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            //Call API
            OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
            output.write(data);
            output.flush();

            //Response in JSON format
            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = response.readLine()) != null) {
                result += line;
            }

            //Interpretation of JSON to extract the shorter URL
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(result, Map.class);

            output.close();
            response.close();

            return (String) map.get("id"); //If everything is working, then return the shortener URL
        } catch (Exception e) {}
        
        return longURL; //In case of errors or problems, just return the full and long URL
    }

    /**
     * 
     * @param shortURL
     * @return 
     */
    public static JSONObject getURLJSONObject(String shortURL) {
        try {
            //Creation of one HTTP connection
            URL url = new URL(GOOGLE_SHORTENER_URL + GOOGLE_SHORTENER_URL_KEY + GOOGLE_SHORTENER_URL_OPTION_shortURL + shortURL + GOOGLE_SHORTENER_URL_OPTION_projection);

            //Preparing the HTTP request
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-length", "0");
            
            //Check status of response
            int status = connection.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    //Response in JSON format
                    BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = response.readLine()) != null) {
                        sb.append(line);
                    }
                    response.close();
                    //JSONObject returned
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(sb.toString());
                    return json;
            }
        } catch(Exception e) {}
        
        return null;
    }
    
    /**
     * 
     * @param shortURL
     * @return 
     */
    public static int getClicksShortenerURL(String shortURL) {
        JSONObject objectURL = getURLJSONObject(shortURL);
        return new Integer((String)((JSONObject)((JSONObject)objectURL.get("analytics")).get("allTime")).get("shortUrlClicks"));
    }
    
    /**
     * 
     * @param shortURL
     * @return 
     */
    public static String getLongURL(String shortURL) {
        JSONObject objectURL = getURLJSONObject(shortURL);
        return (String) objectURL.get("longUrl");
    }
    
//    public static void main(String[] args) {
//        String shortURL = getShortenerURL("http://www.cdt.ch");
////        String shortURL = "http://goo.gl/14Gmwu";
//        System.out.println(shortURL);
//        System.out.println(getClicksShortenerURL(shortURL));
//        System.out.println(getLongURL(shortURL));
//    }
    
}