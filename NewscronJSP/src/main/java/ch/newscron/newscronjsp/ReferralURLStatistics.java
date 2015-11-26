package ch.newscron.newscronjsp;

import ch.newscron.referral.*;
import ch.newscron.registration.*;
import ch.newscron.referralUrlUtils.ShortenerURL;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Din
 */
public class ReferralURLStatistics {
    public static String domain = "http://localhost:8080/";
   
    public String showStatisticsTable(String userId) throws IOException {
        System.out.println("USSSERRID: " + userId);
        List<UserReferralURL> ReferralURLs = ReferralManager.getUserReferralURLs(Long.parseLong(userId));
        String toReturn = "<h3> Customer ID: " + userId + "</h3>" 
                        + "<table border='1' class=\"center\"> <tr> <td> ReferralURLs </td> <td> # of Clicks </td> <td> # registered Users </td> </tr>";
        
        for (UserReferralURL ReferralURLData : ReferralURLs) {
            String referralURL = ReferralURLData.getReferralURL();
            System.out.println("REFERRALURL: " + referralURL);

            toReturn += "<tr> <td> <a href='" + referralURL + "'>" + referralURL + "</a> </td>";
            toReturn += "<td>" + ShortenerURL.getClicksReferralURL(referralURL) + "</td>";
            
            int numbRegisteredUsers = ReferralManager.numberOfRegisteredUsers(referralURL);
            toReturn += "<td>" + numbRegisteredUsers + "</td>";
            
            String longURL = ShortenerURL.getLongURL(referralURL);
            String getDataURL = domain + "decode/" + longURL.split("/")[longURL.split("/").length-1];
            toReturn += "<td> <form action='" + getDataURL +  "'> <button type='submit'> Decode data </button> </form> </tr>";
        }
        toReturn += "</table>";
        return toReturn;
    }
    
    public String showUserTable() throws IOException {
        List<User> userList = UserRegistration.getAllUsers();
        
        String toReturn = "<h3> All Users </h3>" 
                        + "<table border='1' class=\"center\"> "
                        + "<tr> <td colspan='4'> User Table </td> <td class='red' colspan='3'> referralURL Table </td> </tr>"
                        + "<tr> <th> Name </th> <th> Last Name </th>"
                        + "<th> email </th> <th> campaignId </th> "
                        + "<th class='red'> invitedBy </th> <th class='red'> invitationURL </th> </tr>";
        
        for (User user : userList) {
            toReturn += "<tr> <td>" + user.getFirstName() + "</td>";
            toReturn += "<td>" + user.getLastName() + "</td>";
            toReturn += "<td>" + user.getEmail() + "</td>";
            toReturn += "<td>" + user.getCampaignId() + "</td>";
            toReturn += "<td class='red'>" + user.getInvitedBy() + "</td>";
            toReturn += "<td class='red'>" + user.getInvitationURL() + "</td>";
        }
        
        toReturn += "</table>";
        
        return toReturn;
    }
    
}
