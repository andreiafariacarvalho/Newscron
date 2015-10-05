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
    
//    @Test
//    public void setURLtoEncodeTest() throws Exception {
//        testUserData.setURLtoEncode();
//        assertTrue("vKnxBLGBGdG3PIibiZlv4-9VO5Cjq3G6mytrWdjaHGIjYgG84x27M8VxZr4sjRX7PBUXyWw7Ua2j5ANd5KZ7m3WJdxlJNWaD2FKkkE20tfPGFgL1IOv_9AlKZ4h6qLHKZSTlzppl5pi7Q24TnbUBrO1MQisqaeOtbxo4XvRvGcA1eOHz3gWH_dB7hU3n_CbWcv3V2nrwAyblMTMEF5x0yA".equals(testUserData.getURLtoEncode()));
//    } 
//    @Test
//    public void getURLDecodedTest() throws Exception {
//        testUserData.setURLtoEncode();
//        assertTrue(testUserData.getURLDecoded().equals("{\"custID\":\"1234567891234\",\"rew1\":\"10%\",\"rew2\":\"5\",\"val\":\"10/10/2010\"}"));
//    } 
//    
//    @Test
//    public void getFullURL() throws Exception {
//        testUserData.setURLtoEncode();
//        assertTrue(testUserData.getFullURL().equals("http://localhost:8080/invite/vKnxBLGBGdG3PIibiZlv4-9VO5Cjq3G6mytrWdjaHGIjYgG84x27M8VxZr4sjRX7PBUXyWw7Ua2j5ANd5KZ7m3WJdxlJNWaD2FKkkE20tfPGFgL1IOv_9AlKZ4h6qLHKZSTlzppl5pi7Q24TnbUBrO1MQisqaeOtbxo4XvRvGcA1eOHz3gWH_dB7hU3n_CbWcv3V2nrwAyblMTMEF5x0yA"));
//    } 
   
}
