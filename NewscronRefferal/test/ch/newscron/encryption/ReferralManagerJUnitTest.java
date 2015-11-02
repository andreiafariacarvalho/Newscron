package ch.newscron.encryption;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ch.newscron.referral.ReferralManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    ReferralManager refManager;
    
    public ReferralManagerJUnitTest() {

    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {

    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void connectTest() {
//        Connection con = ReferralManager.connect();
        
    }
     
//    @Test
//    public void insertShortURLTest() {
//        
//    }
    
    @Test
    public void selectAllShortURLsTest() {
        
    }   
    
    @Test
    public void selectSingularCustomerShortURLsTest() {
        
    } 
    
    @Test
    public void disconnectTest() {
        
    }
    
    @Test
    public void writeResultSetToListTest() {
        
    }    
}
