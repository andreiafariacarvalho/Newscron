package ch.newscron.encryption;

import com.google.api.services.urlshortener.model.AnalyticsSnapshot;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;

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

    public static String shortenUrl(String longUrl) {
        String data = "{\"longUrl\": \"" + longUrl + "\"}";

        try {
            // création d'une connection HTTP
            URL url = new URL(GOOGLE_SHORTENER_URL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // appel de l'API
            OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
            output.write(data);
            output.flush();

            // réception de la réponse au format JSON
            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = response.readLine()) != null) {
                result += line;
            }

            // interprétation du flux JSON pour extraire l'url réduite
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

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        System.out.println(shortenUrl("http://www.inf.usi.ch"));
        
//        HttpURLConnection c = (HttpURLConnection) new URL(GOOGLE_SHORTENER_URL).openConnection();
//        c.setRequestMethod("GET");
//        InputStream inputStream = c.getInputStream();
        
//        URL url = new URL(GOOGLE_SHORTENER_URL);
//        AnalyticsSnapshot analytics = new AnalyticsSnapshot();
//        analytics.set("http://www.inf.usi.ch", url);
//        System.out.println(analytics.getShortUrlClicks());
        
//        URL url = new URL(GOOGLE_SHORTENER_URL);
//
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.setDoOutput(true);
//        connection.setRequestProperty("Content-Type", "application/json");
//        OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
//        System.out.println(output.toString());
    }
    
    
}