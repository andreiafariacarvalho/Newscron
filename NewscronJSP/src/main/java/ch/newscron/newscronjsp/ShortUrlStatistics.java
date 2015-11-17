package ch.newscron.newscronjsp;

import ch.newscron.referral.*;
import ch.newscron.registration.*;
import ch.newscron.shortUrlUtils.ShortenerURL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Din
 */
public class ShortUrlStatistics {

    public ArrayList<String> processData(String custID) {  
        List<ShortURLDataHolder> data = ReferralManager.selectSingularCustomerShortURLs(Long.parseLong(custID));
        ArrayList<String> resultData = new ArrayList<>();
        for (ShortURLDataHolder shortUrl : data) {
            resultData.add(shortUrl.getShortURL());
        }
        return resultData;
    }
    
    public String showStatisticsTable(String custID) throws IOException {
        ArrayList<String> shortURLs = processData(custID);
        String toReturn = "<h3> Customer ID: " + custID + "</h3>" 
                        + "<table border='1' class=\"center\"> <tr> <td> shortURLs </td> <td> # of Clicks </td> </tr>";
        for (String shortUrl : shortURLs) {
            toReturn += "<tr> <td> <a href='" + shortUrl + "'>" + shortUrl + "</a> </td>";
            toReturn += "<td>" + ShortenerURL.getClicksShortURL(shortUrl) + "</td>";
            String longURL = ShortenerURL.getLongURL(shortUrl);
            String getDataURL = "http://localhost:8080/decode/" + longURL.split("/")[longURL.split("/").length-1];
            toReturn += "<td> <form action='" + getDataURL +  "'> <button type='submit'> Decode data </button> </form> </tr>";
        }
        toReturn += "</table>";
        return toReturn;
    }
    
    public String showUserTable() throws IOException, SQLException {
        ResultSet rs = UserRegistration.selectAllUsers();
        
        String toReturn = "<h3> All Users </h3>" 
                        + "<table border='1' class=\"center\"> "
                        + "<tr> <td colspan='4'> User Table </td> <td class='red' colspan='2'> ShortURL Table </td> </tr>"
                        + "<tr> <td> Name </td> <td> Last Name </td>"
                        + "<td> email </td> <td> campaignId </td> <td class='red'> invitedBy </td> <td class='red'> invitationURL </td> </tr>";
        
        String name;
        String lastName;
        String email;
        String campaignId;
        String invitedBy;
        String invitationURL;
        
        //LOOP OVER RESULT SET
        while (rs.next()) {
                name = rs.getString("name");
                lastName = rs.getString("lastName");
                email = rs.getString("email");
                campaignId = rs.getString("campaignId");
                invitedBy = rs.getString("custID");
                invitationURL = rs.getString("shortURL");

        toReturn += "<tr> <td>" + name + "</td>";
        toReturn += "<td>" + lastName + "</td>";
        toReturn += "<td>" + email + "</td>";
        toReturn += "<td>" + campaignId + "</td>";
        toReturn += "<td class='red'>" + invitedBy + "</td>";
        toReturn += "<td class='red'>" + invitationURL + "</td>";
        }
        
        toReturn += "</table>";
                
        
        return toReturn;
    }
    
}
