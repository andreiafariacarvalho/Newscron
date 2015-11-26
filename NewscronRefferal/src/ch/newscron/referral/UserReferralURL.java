/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.referral;

/**
 *
 * @author Din
 */
public class UserReferralURL {
    private final long userId;
    private final String referralURL;
    
    public UserReferralURL(long userId, String referralURL) {
        this.userId = userId;
        this.referralURL = referralURL;
    }
    
    public long getuserId() {
        return userId;
    }
    
    public String getReferralURL() {
        return referralURL;
    }
}
