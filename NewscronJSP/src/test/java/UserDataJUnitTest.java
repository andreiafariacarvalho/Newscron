/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ch.newscron.newscronjsp.UserData;
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
public class UserDataJUnitTest {
    
    private String custID = "1234567891234"; //long?
    private String rew1 = "10%";
    private String rew2 = "5";
    private String val = "10/10/2010";
    
    UserData testUserData = new UserData();
    
    public UserDataJUnitTest() {
        testUserData.setCustID(custID);
        testUserData.setRew1(rew1);
        testUserData.setRew2(rew2);
        testUserData.setVal(val);
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
    public void setURLtoEncodeTest() throws Exception {
//        testUserData.setURLtoEncode();
//        assertTrue("N-2nYV46WtZCR4a_zFqonP3jOp1GGMpuaTpU-WvI81RyM69kxYhelc5hUfLSr1MT4t9WFUVe9VvTwHzB2tg-VFFDM8646JgbKf0HdRzpG24".equals(testUserData.getURLtoEncode()));
    } 
    @Test
    public void getURLtoEncodeTest() {
        
    } 

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
   
}
