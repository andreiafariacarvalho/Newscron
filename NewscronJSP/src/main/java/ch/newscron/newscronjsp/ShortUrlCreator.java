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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;

/**
 *
 * @author Din
 */

public class ShortUrlCreator extends HttpServlet  {
    public static String domain = "http://localhost:8080/";
    @Override
     public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
    {
        try {        
            String customerId = request.getParameter("customerId");
            String rew1 = request.getParameter("rew1");
            String rew2 = request.getParameter("rew2");
            String val = request.getParameter("val");
            
            String urlEncoded = encodeUrl(customerId, rew1, rew2, val);
            if (urlEncoded==null) {
                response.sendRedirect(domain); 
            } else {
            insertToDatabase(Long.parseLong(customerId), domain + "referral/" + urlEncoded);
            String redirectURL = domain + "userShortUrlStats?customerId="+customerId;
            response.sendRedirect(redirectURL);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ShortUrlCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
      
  }
  // Method to handle POST method request.
    @Override
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
     doGet(request, response);
  }

    private JSONObject createJSON(String customerId, String rew1, String rew2, String val) {
        JSONObject obj = new JSONObject();
        obj.put("custID", customerId);
        obj.put("rew1", rew1);
        obj.put("rew2", rew2);
        obj.put("val", val);
        return obj;
    }
    
    private String encodeUrl(String customerId, String rew1, String rew2, String val) throws Exception {
        JSONObject fullParam = createJSON(customerId, rew1, rew2, val);
        return Encryption.encode(fullParam);
    }
    
    private boolean insertToDatabase(long customerId, String longURL) {
        String shortURL = ShortenerURL.getShortURL(longURL);
        return ReferralManager.insertShortURL(customerId, shortURL);
    }
}
