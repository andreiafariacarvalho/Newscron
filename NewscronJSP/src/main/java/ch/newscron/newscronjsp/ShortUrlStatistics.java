package ch.newscron.newscronjsp;

import ch.newscron.referral.*;
import ch.newscron.registration.*;
import ch.newscron.shortUrlUtils.ShortenerURL;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Din
 */
public class ShortUrlStatistics {
    
    public String showStatisticsTable(String custID) throws IOException {
        List<CustomerShortURL> shortURLs = ReferralManager.getCustomerShortURLs(Long.parseLong(custID));
        String toReturn = "<h3> Customer ID: " + custID + "</h3>" 
                        + "<table border='1' class=\"center\"> <tr> <td> shortURLs </td> <td> # of Clicks </td> <td> # registered Users </td> </tr>";
        
        String shortUrl;
        for (CustomerShortURL shortUrlData : shortURLs) {
            shortUrl = shortUrlData.getShortURL();
            toReturn += "<tr> <td> <a href='" + shortUrl + "'>" + shortUrl + "</a> </td>";
            toReturn += "<td>" + ShortenerURL.getClicksShortURL(shortUrl) + "</td>";
            
            int numbRegisteredUsers = ReferralManager.numberOfRegisteredUsers(shortUrl);
            toReturn += "<td>" + numbRegisteredUsers + "</td>";
            
            String longURL = ShortenerURL.getLongURL(shortUrl);
            String getDataURL = "http://localhost:8080/decode/" + longURL.split("/")[longURL.split("/").length-1];
            toReturn += "<td> <form action='" + getDataURL +  "'> <button type='submit'> Decode data </button> </form> </tr>";
        }
        toReturn += "</table>";
        return toReturn;
    }
    
    public String showUserTable() throws IOException {
        List<User> userList = UserRegistration.selectAllUsers();
        
        String toReturn = "<h3> All Users </h3>" 
                        + "<table border='1' class=\"center\"> "
                        + "<tr> <td colspan='4'> User Table </td> <td class='red' colspan='2'> ShortURL Table </td> </tr>"
                        + "<tr> <th> Name </th> <th> Last Name </th>"
                        + "<th> email </th> <th> campaignId </th> <th class='red'> invitedBy </th> <th class='red'> invitationURL </th> </tr>";
        
        String name;
        String lastName;
        String email;
        long campaignId;
        String invitedBy;
        String invitationURL;
        
        //LOOP OVER RESULT SET
        for (User userList1 : userList) {
            name = userList1.getFirstName();
            lastName = userList1.getLastName();
            email = userList1.getEmail();
            campaignId = userList1.getCampaignId();
            invitedBy = userList1.getInvitedBy();
            invitationURL = userList1.getInvitationURL();
            
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
