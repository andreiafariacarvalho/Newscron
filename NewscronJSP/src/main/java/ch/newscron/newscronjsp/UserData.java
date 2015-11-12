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
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Din
 */

public class UserData extends HttpServlet  {

    String custID;
    String rew1;
    String rew2;
    String val;
    String urlEncoded;

    public UserData() {
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
            
            setURLtoEncode();
            shortUrlStatistics shortUrlHandler = new shortUrlStatistics();
            shortUrlHandler.saveURL(custID, getFullURL());
            String redirectURL = "http://localhost:8080/NextPage?custID="+custID;
            response.sendRedirect(redirectURL);
            
        } catch (Exception ex) {
            Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
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

    
    public JSONObject createJSON(String custID, String rew1, String rew2, String val) {
        JSONObject obj = new JSONObject();
        obj.put("custID", custID);
        obj.put("rew1", rew1);
        obj.put("rew2", rew2);
        obj.put("val", val);
        return obj;
    }
    public void setURLtoEncode() throws Exception {
        JSONObject fullParam = createJSON(custID, rew1, rew2, val);
        urlEncoded = Encryption.encode(fullParam);
    }
    
    public String getURLtoEncode() {
        return urlEncoded;
    }
    
    public String getURLDecoded() throws Exception {
        return Encryption.decode(urlEncoded.trim());
    }
    
    
    public String getFullURL () {
        return "http://localhost:8080/invite/" + urlEncoded;
    }
    
}
