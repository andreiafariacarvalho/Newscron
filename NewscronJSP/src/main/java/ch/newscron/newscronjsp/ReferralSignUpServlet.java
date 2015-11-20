/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.newscronjsp;
import org.json.simple.JSONObject;
import ch.newscron.encryption.Encryption;
import ch.newscron.referral.ReferralManager;
import ch.newscron.shortUrlUtils.ShortenerURL;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Din
 */

public class ReferralSignUpServlet extends HttpServlet  {
    public static String domain = "http://localhost:8080/";
    @Override
     public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
    {
        try {        
            
            String urlPath = request.getRequestURI();
            String encodedDataString = urlPath.split("/")[urlPath.split("/").length-1];
            System.out.println("encodedDataString: " + encodedDataString);
            
            String decodedDataString = Encryption.decode(encodedDataString);
            
            if(decodedDataString != null) {
                JSONParser parser = new JSONParser();
                JSONObject decodedJSON = (JSONObject) parser.parse(decodedDataString);

                String dateValidity = (String) decodedJSON.get("val");
                String rewardNewUser = (String) decodedJSON.get("rew2");

                if(!isDateExpired(dateValidity)) {
                    response.sendRedirect(domain + "sign_up/" + encodedDataString + "/" + rewardNewUser);
                } else {
                    // Date validity expired
                    response.sendRedirect(domain + "sign_up/");
                }
            } else{
                // Null from decoding: invalid URL
                response.sendRedirect(domain + "sign_up/");
            }
            
            
//            response.sendRedirect();
            
        } catch (Exception ex) {
            response.sendRedirect(domain + "sign_up/");
            Logger.getLogger(ShortUrlCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
      
  }
  // Method to handle POST method request.
//    @Override
//  public void doPost(HttpServletRequest request,
//                     HttpServletResponse response)
//      throws ServletException, IOException {
//     doGet(request, response);
//  }

    private static boolean isDateExpired(String dateValidity) {
        //datevalidity format assumption: dd.mm.yy
        Date referralDate = new Date(dateValidity);
        Date today = new Date();
        return true;
    }

}
