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
//        
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307", "root", "");
//            Statement statement = connection.createStatement();
//            int count = statement.executeUpdate("CREATE SCHEMA testShortURL");
//            statement.close();
//            connection.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(ReferralManagerJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
//        
//        String JDBCDriver = "jdbc:mysql://%s:%s/%s";
//        String server = "localhost";
//        String port = "3307";
//        String database = "bla1";
//        String DBurl = String.format(JDBCDriver, server, port, database);
//    
//        //Credentials for database
//        String username = "root";
//        String password = "";
        
        
        
        
//        refManager = new ReferralManager("jdbc:mysql://%s:%s");
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
     
    @Test
    public void insertShortURLTest() {
        
    }
    
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
