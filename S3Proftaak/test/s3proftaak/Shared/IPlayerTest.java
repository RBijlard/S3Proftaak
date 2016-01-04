///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package s3proftaak.Shared;
//
//import java.rmi.RemoteException;
//import junit.framework.TestCase;
//import s3proftaak.Server.Player;
//
///**
// *
// * @author Alex Ras
// */
//public class IPlayerTest extends TestCase {
//    
//    public IPlayerTest(String testName) {
//        super(testName);
//    }
//
//    /**
//     * Test of getName method, of class IPlayer.
//     */
//    public void testGetName() throws Exception {
//        System.out.println("getName");
//        Player p = new Player("Alex");
//        IPlayer instance = (IPlayer) p;
//        String expResult = "Alex";
//        String result = instance.getName();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of isReady method, of class IPlayer.
//     */
//    public void testIsReady() throws Exception {
//        System.out.println("isReady");
//        Player p = new Player("Alex");
//        p.setReady(true);
//        IPlayer instance = (IPlayer) p;
//        boolean expResult = true;
//        boolean result = instance.isReady();
//        assertEquals(expResult, result);
//    }
//    
//}
