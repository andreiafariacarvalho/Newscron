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
public class CustomerShortURL {
    private final long customerId;
    private final String shortURL;
    
    public CustomerShortURL(long customerId, String shortURL) {
        this.customerId = customerId;
        this.shortURL = shortURL;
    }
    
    public long getCustomerId() {
        return customerId;
    }
    
    public String getShortURL() {
        return shortURL;
    }
}
