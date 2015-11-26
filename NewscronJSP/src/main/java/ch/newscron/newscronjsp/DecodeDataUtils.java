/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.newscronjsp;

import ch.newscron.encryption.CouponData;
import ch.newscron.encryption.Encryption;
import ch.newscron.referralUrlUtils.ShortenerURL;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Din
 */
public class DecodeDataUtils {
    
    private String userId;
    String encodedURL;
    public static String domain = "http://localhost:8080/";
    private static final DateFormat valDateFormat = new SimpleDateFormat("dd.MM.yy"); // Format of the date used to store the validity
    
    public DecodeDataUtils() {
    }

    public String showURLData() throws ParseException, Exception {
        CouponData url = Encryption.decode(encodedURL.trim());
        userId = Long.toString(url.getUserId());

        if (url == null) {
                return "<p> Invalid URL </p>";
        } else {
            return "<table border='0' class=\"center\"> "
                    + "<tr> " + " <td> userId: </td> <td>" + userId + "</td> " + "</tr> "
                    + "<tr> " + " <td> rew1: </td> <td>" + url.getRew1() + "</td> " + "</tr> "
                    + "<tr> " + " <td> rew2: </td> <td>" + url.getRew2() + "</td> " + "</tr> "
                    + "<tr> " + " <td> val: </td> <td>" + valDateFormat.format(url.getVal()) + "</td> " + "</tr> "
                    + "</table>";
        }
    }
    
    public void setUrlString(String fullURL) {
        encodedURL = fullURL.split("/")[fullURL.split("/").length-1];
    }
    
    public String getShorterUrl() throws IOException {
        return ShortenerURL.getReferralURL(domain + "referral/" + encodedURL);
    }
    
    public String getUserId() {
        return userId;
    }
    
}
