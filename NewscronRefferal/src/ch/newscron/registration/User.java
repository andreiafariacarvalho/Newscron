/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.registration;

/**
 *
 * @author Din
 */
public class User {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final long campaignId;
    private final String invitedBy;
    private final String invitationURL;
    
    public User(String firstName, String lastName, String email, long campaignId, String invitedBy, String invitationURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.campaignId = campaignId;
        this.invitedBy = invitedBy;
        this.invitationURL = invitationURL;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
    public long getCampaignId() {
        return campaignId;
    }
    
    public String getInvitedBy() {
        return invitedBy;
    }
    
    public String getInvitationURL() {
        return invitationURL;
    }
    
    
}
