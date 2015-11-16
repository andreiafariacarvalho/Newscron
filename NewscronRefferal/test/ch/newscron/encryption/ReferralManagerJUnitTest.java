package ch.newscron.encryption;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ch.newscron.referral.ReferralManager;
import ch.newscron.referral.ShortURLDataHolder;
import ch.newscron.shortUrlUtils.ShortenerURL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Din
 */
public class ReferralManagerJUnitTest {
    
    static JSONObject inviteData = new JSONObject();
    static Connection con;
    static String shortURL;
    
    public ReferralManagerJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        inviteData.put("custID", "0");
        inviteData.put("rew1", "40%");
        inviteData.put("rew2", "50%");
        inviteData.put("val", "05/10/2015");
        String stringEncoded = Encryption.encode(inviteData);
        String url = "app.segmento/newscron/invite/%s";
        url = String.format(url, stringEncoded);
        shortURL = ShortenerURL.getShortURL(url);
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        // Delete inserted query
        con = ReferralManager.connect();
        PreparedStatement query = null;
        query = con.prepareStatement("DELETE FROM ShortURL WHERE custId= ? AND shortUrl = ?");
        query.setLong(1, Long.parseLong((String) inviteData.get("custID")));
        query.setString(2, shortURL);
        query.execute();
        ReferralManager.disconnect(con, null);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void connectTest() throws SQLException {
        con = ReferralManager.connect();
        assertFalse(con.isClosed());
        con.close();
    }
     
    @Test
    public void databaseTest() throws SQLException {
        con = ReferralManager.connect();
        int numberShortURLbefore = ReferralManager.selectSingularCustomerShortURLs(Long.parseLong((String) inviteData.get("custID"))).size();
        boolean b = ReferralManager.insertShortURL(Long.parseLong((String) inviteData.get("custID")), shortURL);
        ReferralManager.disconnect(con, null);
        
        con = ReferralManager.connect();
        List<ShortURLDataHolder> listShortURL = ReferralManager.selectSingularCustomerShortURLs(Long.parseLong((String) inviteData.get("custID")));
        assertTrue(listShortURL.size() == numberShortURLbefore + 1);
        ReferralManager.disconnect(con, null);
        
        con = ReferralManager.connect();
        PreparedStatement query = null;
        ResultSet rs = null;
        query = con.prepareStatement("SELECT * FROM ShortURL WHERE custId = ? AND shortUrl = ?");
        query.setLong(1, Long.parseLong((String) inviteData.get("custID")));
        query.setString(2, shortURL);
        rs = query.executeQuery();
        int count = 0;
        while (rs.next()) 
            ++count;
        assertTrue(count == 1);
        ReferralManager.disconnect(con, query, rs);
    }
    
    @Test
    public void disconnectTest() throws SQLException {
        con = ReferralManager.connect();
        assertFalse(con.isClosed());
        ReferralManager.disconnect(con, null);
        assertTrue(con.isClosed());
    }   
}
