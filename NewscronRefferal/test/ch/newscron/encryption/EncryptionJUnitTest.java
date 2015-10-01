/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.newscron.encryption;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andreiafariacarvalho
 */
public class EncryptionJUnitTest {
    
    public EncryptionJUnitTest() {
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
    public void encodeTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException {
//        String nullEncoded = Encryption.encode(null);
//        assertNull(nullEncoded);
//        String string = "helloworld";
//        String stringEncoded = Encryption.encode(string);
//        assertNotNull(stringEncoded);
//        assertFalse(string.equals(stringEncoded));
//        assertTrue(string.length() < stringEncoded.length());
    }
    
    @Test
    public void decodeTest() throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException {
//        String nullDecoded = Encryption.decode(null);
//        assertNull(nullDecoded);
//        String string = "helloworld";
//        String stringEncoded = Encryption.encode(string);
//        String stringDecoded = Encryption.decode(stringEncoded);
//        assertNotNull(stringDecoded);
//        assertTrue(string.equals(stringDecoded));
    }
}
