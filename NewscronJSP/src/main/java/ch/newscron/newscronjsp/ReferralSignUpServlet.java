/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.newscronjsp;
import ch.newscron.encryption.CouponData;
import ch.newscron.encryption.Encryption;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author Din
 */

public class ReferralSignUpServlet extends HttpServlet  {
    private static final String domain = "http://localhost:8080/%s";
    private static final String path = "sign_up/";
//    private static String signupPage = String.format(domain, path);
    private static final DateFormat valDateFormat = new SimpleDateFormat("dd.MM.yy"); // Format of the date used to store the validity

    
    @Override
     public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
    {
        String signupPage = String.format(domain, path);
        try {        
            
            String[] urlPartsPath = request.getRequestURI().split("/"); 
            String encodedDataString = urlPartsPath[urlPartsPath.length-1];
            
            CouponData decodedDataObject = Encryption.decode(encodedDataString);
            
            if(decodedDataObject != null) {

                Date dateValidity = decodedDataObject.getVal();
                if(dateValidity.after(new Date())) { // Date validity not expired & not null from decoding URL
                    String rewardNewUser = (String) decodedDataObject.getRew2();
                    String signupPageReferral = "%s/%s";
                    signupPageReferral = String.format(signupPageReferral, encodedDataString, Base64.encodeBase64URLSafeString(rewardNewUser.getBytes()));
                    signupPage += signupPageReferral;
                }
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ReferralURLCreator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            response.sendRedirect(signupPage);
        }
    }

}
