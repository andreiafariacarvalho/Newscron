package ch.newscron.encryption;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ch.newscron.referral.ReferralManager;
import ch.newscron.referral.UserReferralURL;
import ch.newscron.referralUrlUtils.ShortenerURL;
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
    static String referralURL;
    
    public ReferralManagerJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        inviteData.put("userId", "0");
        inviteData.put("rew1", "40%");
        inviteData.put("rew2", "50%");
        inviteData.put("val", "05/10/2015");
        String stringEncoded = Encryption.encode(inviteData);
        String url = "app.segmento/newscron/invite/%s";
        url = String.format(url, stringEncoded);
        referralURL = ShortenerURL.getReferralURL(url);
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        // Delete inserted query
        con = ReferralManager.connect();
        PreparedStatement query = null;
        query = con.prepareStatement("DELETE FROM referralURL WHERE userId= ? AND referralUrl = ?");
        query.setLong(1, Long.parseLong((String) inviteData.get("userId")));
        query.setString(2, referralURL);
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
        int numberReferralURLbefore = ReferralManager.getUserReferralURLs(Long.parseLong((String) inviteData.get("userId"))).size();
        boolean b = ReferralManager.insertReferralURL(Long.parseLong((String) inviteData.get("userId")), referralURL);
        ReferralManager.disconnect(con, null);
        
        con = ReferralManager.connect();
        List<UserReferralURL> listReferralURL = ReferralManager.getUserReferralURLs(Long.parseLong((String) inviteData.get("userId")));
        assertTrue(listReferralURL.size() == numberReferralURLbefore + 1);
        ReferralManager.disconnect(con, null);
        
        con = ReferralManager.connect();
        PreparedStatement query = null;
        ResultSet rs = null;
        query = con.prepareStatement("SELECT * FROM referralURL WHERE userId = ? AND referralUrl = ?");
        query.setLong(1, Long.parseLong((String) inviteData.get("userId")));
        query.setString(2, referralURL);
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
