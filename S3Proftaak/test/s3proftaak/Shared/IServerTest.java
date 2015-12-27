///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package s3proftaak.Shared;
//
//import junit.framework.TestCase;
//import s3proftaak.Client.Visuals.Listeners.GameListener;
//import s3proftaak.Server.Lobby;
//import s3proftaak.Server.ServerAdministration;
//
///**
// *
// * @author Alex Ras
// */
//public class IServerTest extends TestCase {
//
//    public IServerTest(String testName) {
//        super(testName);
//    }
//
//    /**
//     * Test of createLobby method, of class IServer.
//     */
//    public void testCreateLobby() throws Exception {
//        System.out.println("createLobby");
//
//        String lobbyname = "MyLobby";
//
//        ServerAdministration s = new ServerAdministration();
//        IServer instance = (IServer) s;
//
//        String expResult = new Lobby(lobbyname, 2).getName();
//        String result = instance.createLobby(lobbyname).getName();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getLobbies method, of class IServer.
//     */
//    public void testGetLobbies() throws Exception {
//        System.out.println("getLobbies");
//
//        ServerAdministration s = new ServerAdministration();
//        IServer instance = (IServer) s;
//
//        int expResult = 2;
//
//        instance.createLobby("FirstLobby");
//        instance.createLobby("SecondLobby)");
//
//        int result = instance.getLobbies().size();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of addListener method, of class IServer.
//     */
//    public void testAddListener() throws Exception {
//        System.out.println("addListener");
//
//        ServerAdministration s = new ServerAdministration();
//        IServer instance = (IServer) s;
//        GameListener g = new GameListener();
//
//        instance.addListener(g, "LobbyList");
//    }
//
//    /**
//     * Test of removeListener method, of class IServer.
//     */
//    public void testRemoveListener() throws Exception {
//        System.out.println("removeListener");
//
//        ServerAdministration s = new ServerAdministration();
//        IServer instance = (IServer) s;
//        GameListener g = new GameListener();
//
//        instance.addListener(g, "LobbyList");
//        instance.removeListener(g, "LobbyList");
//    }
//
//}
