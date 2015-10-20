package ch.newscron.encryption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andreiafariacarvalho
 */
public class ShortenerUrlUtil {

//    public static final String GOOGLE_SHORTENER_URL = "https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyDwu91R6A4EhN-NeAYWrEqecSIn_z-3tmA";
    public static final String GOOGLE_SHORTENER_URL = "https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyDwu91R6A4EhN-NeAYWrEqecSIn_z-3tmA&shortUrl=http://goo.gl/14Gmwu&projection=FULL";
    public static final String webSite = "http://www.inf.usi.ch";
    
    public static String shortenUrl(String longUrl) throws IOException {
        
        String data = "{\"longUrl\": \"" + longUrl + "\"}";

        try {
            // creation of one HTTP connection
            URL url = new URL(GOOGLE_SHORTENER_URL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // call API
            OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
            output.write(data);
            output.flush();

            // response in JSON format
            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = response.readLine()) != null) {
                result += line;
            }

            // interpretation of JSON to extract the shorter URL
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(result, Map.class);
            System.out.println(map.toString());

            output.close();
            response.close();

            return (String) map.get("id");
        } catch (Exception e) {
            return longUrl;
        }
    }

    public static JSONObject getURLJSONObject() throws MalformedURLException, ProtocolException, IOException, ParseException {
        URL url = new URL(GOOGLE_SHORTENER_URL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-length", "0");
        int status = connection.getResponseCode();
        
        switch (status) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(sb.toString());
                return json;
//                JSONObject json2 = (JSONObject) json.get("analytics");
//                JSONObject json3 = (JSONObject) json2.get("allTime");
//                System.out.println(json3.get("shortUrlClicks"));
        }
        return null;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        System.out.println(shortenUrl(webSite));
        
//        Urlshortener shortener = null;
//        UrlHistory history = shortener.url().list().execute();
        
//        JSONObject o1 = getURLJSONObject();
//        JSONObject o2 = (JSONObject) o1.get("analytics");
//        JSONObject o3 = (JSONObject) o2.get("allTime");
//        System.out.println(o3.get("shortUrlClicks"));
//        System.out.println(o1.toString()); // BIG QUESTION!!! JSONArray or JSONObject?!?!?!?!
        
    }
    
}