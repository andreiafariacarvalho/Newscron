/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.newscronjsp;
import org.json.simple.JSONObject;
import ch.newscron.encryption.Encryption;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;
import org.json.simple.parser.JSONParser;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author Din
 */

public class ReferralSignUpServlet extends HttpServlet  {
    private static final String domain = "http://localhost:8080/%s";
    private static final String path = "sign_up/";
    private static String signupPage = String.format(domain, path);
    
    @Override
     public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
    {
        try {        
            
            String[] urlPartsPath = request.getRequestURI().split("/"); 
            String encodedDataString = urlPartsPath[urlPartsPath.length-1];
            
            String decodedDataString = Encryption.decode(encodedDataString);
            
            if(decodedDataString != null) {
                JSONParser parser = new JSONParser();
                JSONObject decodedJSON = (JSONObject) parser.parse(decodedDataString);

                String dateValidity = (String) decodedJSON.get("val");
                if(isDateValid(dateValidity)) { // Date validity not expired & not null from decoding URL
                    String rewardNewUser = (String) decodedJSON.get("rew2");
                    String signupPageReferral = "%s/%s";
                    signupPageReferral = String.format(signupPageReferral, encodedDataString, Base64.encodeBase64URLSafeString(rewardNewUser.getBytes()));
                    signupPage += signupPageReferral;
                }
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ShortUrlCreator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            response.sendRedirect(signupPage);
        }
    }

    private static boolean isDateValid(String dateValidity) {
        try {
            //datevalidity format assumption: dd.mm.yy
            Date referralDate = new SimpleDateFormat("dd.MM.yy").parse(dateValidity);
            Date today = new Date();
            if(today.before(referralDate)) {
                return true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(ReferralSignUpServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
