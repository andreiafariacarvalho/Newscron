/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.newscronjsp;
import org.json.simple.JSONObject;
import ch.newscron.encryption.Encryption;
import ch.newscron.referral.ReferralManager;
import ch.newscron.referralUrlUtils.ShortenerURL;
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

public class ReferralURLCreator extends HttpServlet  {
    public static String domain = "http://localhost:8080/";
    @Override
     public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
    {
        try {        
            String userId = request.getParameter("userId");
            String rew1 = request.getParameter("rew1");
            String rew2 = request.getParameter("rew2");
            String val = request.getParameter("val");
            
            String urlEncoded = encodeUrl(userId, rew1, rew2, val);
            if (urlEncoded==null) {
                response.sendRedirect(domain); 
            } else {
            insertToDatabase(Long.parseLong(userId), domain + "referral/" + urlEncoded);
            String redirectURL = domain + "userReferralURLStats?userId="+userId;
            response.sendRedirect(redirectURL);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ReferralURLCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
      
  }
  // Method to handle POST method request.
    @Override
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
     doGet(request, response);
  }

    private JSONObject createJSON(String userId, String rew1, String rew2, String val) {
        JSONObject obj = new JSONObject();
        obj.put("userId", userId);
        obj.put("rew1", rew1);
        obj.put("rew2", rew2);
        obj.put("val", val);
        return obj;
    }
    
    private String encodeUrl(String userId, String rew1, String rew2, String val) throws Exception {
        JSONObject fullParam = createJSON(userId, rew1, rew2, val);
        return Encryption.encode(fullParam);
    }
    
    private boolean insertToDatabase(long userId, String longURL) {
        String referralURL = ShortenerURL.getReferralURL(longURL);
        return ReferralManager.insertReferralURL(userId, referralURL);
    }
}
