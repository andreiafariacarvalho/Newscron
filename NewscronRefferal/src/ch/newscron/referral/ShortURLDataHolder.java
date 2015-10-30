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
public class ShortURLDataHolder {
    private int custID;
    private String shortURL;
    
    public ShortURLDataHolder(int CustID, String shortURL) {
        this.custID = custID;
        this.shortURL = shortURL;
    }
    
    public int getCustID() {
        return custID;
    }
    
    public String getShortURL() {
        return shortURL;
    }
}
