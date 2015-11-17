/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.newscronjsp;
import org.json.simple.JSONObject;
import ch.newscron.encryption.Encryption;
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

public class ShortUrlUtils extends HttpServlet  {

    String custID;
    String rew1;
    String rew2;
    String val;

    public ShortUrlUtils() {
    }
    
    @Override
     public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
    {
        try {
            
            custID = request.getParameter("custID");
            rew1 = request.getParameter("rew1");
            rew2 = request.getParameter("rew2");
            val = request.getParameter("val");
            
            String urlEncoded = getURLtoEncode();
            shortUrlStatistics shortUrlHandler = new shortUrlStatistics();
            shortUrlHandler.saveURL(custID, "http://localhost:8080/invite/" + urlEncoded);
            String redirectURL = "http://localhost:8080/userShortUrlStats?custID="+custID;
            response.sendRedirect(redirectURL);
            
        } catch (Exception ex) {
            Logger.getLogger(ShortUrlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
      
  }
  // Method to handle POST method request.
    @Override
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
     doGet(request, response);
  }
  
    public String getCustID() { return custID; }

    public String getRew1() { return rew1; }

    public String getRew2() { return rew2; }
    
    public String getVal() { return val; }

    private JSONObject createJSON(String custID, String rew1, String rew2, String val) {
        JSONObject obj = new JSONObject();
        obj.put("custID", custID);
        obj.put("rew1", rew1);
        obj.put("rew2", rew2);
        obj.put("val", val);
        return obj;
    }
    
    private String getURLtoEncode() throws Exception {
        JSONObject fullParam = createJSON(custID, rew1, rew2, val);
        return Encryption.encode(fullParam);
    }
}
