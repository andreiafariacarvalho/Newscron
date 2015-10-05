/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ch.newscron.newscronjsp.ReadInviteData;
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
public class ReadInviteDataJUnitTest {
    
    ReadInviteData testObj = new ReadInviteData();
    
    public ReadInviteDataJUnitTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
//    @Test
//    public void parseURLTest() throws Exception {
//        String encodedURL_valid = "vKnxBLGBGdG3PIibiZlv4-9VO5Cjq3G6mytrWdjaHGIjYgG84x27M8VxZr4sjRX7PBUXyWw7Ua2j5ANd5KZ7m3WJdxlJNWaD2FKkkE20tfPGFgL1IOv_9AlKZ4h6qLHKZSTlzppl5pi7Q24TnbUBrO1MQisqaeOtbxo4XvRvGcA1eOHz3gWH_dB7hU3n_CbWcv3V2nrwAyblMTMEF5x0yA";
//        testObj.setEncodedURL(encodedURL_valid);
//        String res_valid = testObj.parseURL();
//        assertTrue(res_valid.equals("<table border='1'> <tr>  <td> custID: </td> <td>1234567891234</td> </tr> <tr>  <td> rew1: </td> <td>10%</td> </tr> <tr>  <td> rew2: </td> <td>5</td> </tr> <tr>  <td> val: </td> <td>10/10/2010</td> </tr> </table>"));
//        
//        String encodedURL_invalid = "vKnxBLGBGdG3PIibiZlv4-9VO5Cjq4G6mytrWdjaHGIjYgG84x27M8VxZrxsjRX7PBUXyWw7Ua2j5ANd5Kl7m3WJdxlJNWaD2FKkkE20tfPGFgL1IOv_9AlKZ4h6qLHKZSTlzppl5pi7Q24TnbUBrO1MQisqaeOtbxo4XvRvGcA1eOHz3gWH_dB7hU3n_CbWcv3V2nrwAyblMTMEF5x0yA";
//        testObj.setEncodedURL(encodedURL_invalid);
//        String res_invalid = testObj.parseURL();
//        assertTrue(res_invalid.equals("<p> Invalid URL </p>"));
//        
//        String encodedURL_corrupt = "vKnxBLGBGdG3PIibiZlv4w8DpyQfvPhtz_7HayuA6b2-DC1M45RL7LcBc_p2IIXl4eXDphGxx5esQx74kE-txVij1KuqgW6J7pC2yCSIDxVfJkfHdubKRdvC7aFhclXq";
//        testObj.setEncodedURL(encodedURL_corrupt);
//        String res_corrupt = testObj.parseURL();
//        assertTrue(res_corrupt.equals("<p> Corrupt URL - invalid data! </p>"));       
//    }
//    
//    @Test
//    public void getDataFromURLTest() throws Exception {
//        testObj.getDataFromURL("http://localhost:8080/invite/vKnxBLGBGdG3PIibiZlv4-9VO5Cjq3G6mytrWdjaHGIjYgG84x27M8VxZr4sjRX7PBUXyWw7Ua2j5ANd5KZ7m3WJdxlJNWaD2FKkkE20tfPGFgL1IOv_9AlKZ4h6qLHKZSTlzppl5pi7Q24TnbUBrO1MQisqaeOtbxo4XvRvGcA1eOHz3gWH_dB7hU3n_CbWcv3V2nrwAyblMTMEF5x0yA");
//        assertTrue(testObj.getEncodedURL().equals("vKnxBLGBGdG3PIibiZlv4-9VO5Cjq3G6mytrWdjaHGIjYgG84x27M8VxZr4sjRX7PBUXyWw7Ua2j5ANd5KZ7m3WJdxlJNWaD2FKkkE20tfPGFgL1IOv_9AlKZ4h6qLHKZSTlzppl5pi7Q24TnbUBrO1MQisqaeOtbxo4XvRvGcA1eOHz3gWH_dB7hU3n_CbWcv3V2nrwAyblMTMEF5x0yA"));
//    }
}
