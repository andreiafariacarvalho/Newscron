package ch.newscron.newscronjsp;

import ch.newscron.referral.*;
import ch.newscron.shortUrlUtils.ShortenerURL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Din
 */
public class shortUrlStatistics {
    
    private String shortURL;
    
    public void saveURL(String custId, String longURL) throws IOException {
        shortURL = ShortenerURL.getShortURL(longURL);
        insertToDatabase(custId, shortURL);
    }
    
    public String getShortURL() {
        return shortURL;
    }
    
    public List<ShortURLDataHolder> getCustIDShortURLs(String custId) {
        int custID = Integer.parseInt(custId);
        return ReferralManager.selectSingularCustomerShortURLs(custID);
    }
    
    
    public void insertToDatabase(String custId, String shortURL) {
        int custID = Integer.parseInt(custId);
        ReferralManager.insertShortURL(custID, shortURL);
    }

    public ArrayList<String> processData(List<ShortURLDataHolder> data) {
        ArrayList<String> resultData = new ArrayList<>();
        for (ShortURLDataHolder shortUrl : data) {
            resultData.add(shortUrl.getShortURL());
        }
        return resultData;
    }
    
    public String showStatisticsTable(String custId) throws IOException {
        System.out.println("CUSTID:::: " + custId);
        ArrayList<String> shortURLs = processData(getCustIDShortURLs(custId));
        String toReturn = "<h3> Customer ID: " + custId + "</h3>" 
                        + "<table border='1' class=\"center\"> <tr> <td> shortURLs </td> <td> # of Clicks </td> </tr>";
        for (String shortUrl : shortURLs) {
            toReturn += "<tr> <td> <a href='" + shortUrl + "'>" + shortUrl + "</a> </td>";
            toReturn += "<td>" + ShortenerURL.getClicksShortURL(shortUrl) + "</td> </tr>";
        }
        toReturn += "</table>";
        return toReturn;
    }
    
}
