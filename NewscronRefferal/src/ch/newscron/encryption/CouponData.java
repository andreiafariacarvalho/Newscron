/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.encryption;

import java.util.Date;

/**
 *
 * @author andreiafariacarvalho
 */
public class CouponData {
    
    private final long userId;
    private final String rew1;
    private final String rew2;
    private final Date val;
    
    public CouponData(long userId, String rew1, String rew2, Date val) {
        this.userId = userId;
        this.rew1 = rew1;
        this.rew2 = rew2;
        this.val = val;
    }
    
    public long getUserId() {
        return userId;
    }
    
    public String getRew1() {
        return rew1;
    }
    
    public String getRew2() {
        return rew2;
    }
    
    public Date getVal() {
        return val;
    }
    
}
