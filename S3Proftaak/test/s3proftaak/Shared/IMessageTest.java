/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Roel
 */
public class IMessageTest {

    public IMessageTest() {
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

    /**
     * Test of getSender method, of class IMessage.
     */
    @Test
    public void testGetSender() {
        System.out.println("getSender");
        IMessage instance = new IMessageImpl();
        String expResult = "";        
        String result = instance.getSender();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("Getsender is not equal to the sender.");
    }

    /**
     * Test of getMessage method, of class IMessage.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        IMessage instance = new IMessageImpl();
        String expResult = "";
        String result = instance.getMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("GetMessage is not equal to the message.");
    }

    public class IMessageImpl implements IMessage {

        public String getSender() {
            return "";
        }

        public String getMessage() {
            return "";
        }

        public String toString() {
            return "";
        }
    }
}
