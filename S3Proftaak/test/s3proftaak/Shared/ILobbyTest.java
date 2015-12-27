/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

import java.rmi.RemoteException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import s3proftaak.fontys.RemotePropertyListener;
import s3proftaak.util.CustomException;

/**
 *
 * @author Roel
 */
public class ILobbyTest {

    public ILobbyTest() {
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
     * Test of sendMessage method, of class ILobby.
     */
    @Test
    public void testSendMessage() throws Exception {
        System.out.println("sendMessage");
        IMessage message = null;
        ILobby instance = new ILobbyImpl();
        instance.sendMessage(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateLevel method, of class ILobby.
     */
    @Test
    public void testUpdateLevel() throws Exception {
        System.out.println("updateLevel");
        String level = "";
        ILobby instance = new ILobbyImpl();
        instance.updateLevel(level);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toggleReadyState method, of class ILobby.
     */
    @Test
    public void testToggleReadyState() throws Exception {
        System.out.println("toggleReadyState");
        String username = "";
        ILobby instance = new ILobbyImpl();
        instance.toggleReadyState(username);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updatePlayers method, of class ILobby.
     */
    @Test
    public void testUpdatePlayers() throws Exception {
        System.out.println("updatePlayers");
        ILobby instance = new ILobbyImpl();
        instance.updatePlayers();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updatePlayer method, of class ILobby.
     */
    @Test
    public void testUpdatePlayer() throws Exception {
        System.out.println("updatePlayer");
        String username = "";
        PlayerPosition pp = null;
        ILobby instance = new ILobbyImpl();
        instance.updatePlayer(username, pp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateObject method, of class ILobby.
     */
    @Test
    public void testUpdateObject() throws Exception {
        System.out.println("updateObject");
        int id = 0;
        boolean state = false;
        ILobby instance = new ILobbyImpl();
        instance.updateObject(id, state);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateMoveableObject method, of class ILobby.
     */
    @Test
    public void testUpdateMoveableObject() throws Exception {
        System.out.println("updateMoveableObject");
        int id = 0;
        int dx = 0;
        ILobby instance = new ILobbyImpl();
        instance.updateMoveableObject(id, dx);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closedGame method, of class ILobby.
     */
    @Test
    public void testClosedGame() throws Exception {
        System.out.println("closedGame");
        ILobby instance = new ILobbyImpl();
        instance.closedGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadedGame method, of class ILobby.
     */
    @Test
    public void testLoadedGame() throws Exception {
        System.out.println("loadedGame");
        String username = "";
        ILobby instance = new ILobbyImpl();
        instance.loadedGame(username);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of kickPlayer method, of class ILobby.
     */
    @Test
    public void testKickPlayer() throws Exception {
        System.out.println("kickPlayer");
        String username = "";
        ILobby instance = new ILobbyImpl();
        instance.kickPlayer(username);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class ILobby.
     */
    @Test
    public void testGetName() throws Exception {
        System.out.println("getName");
        ILobby instance = new ILobbyImpl();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stopGame method, of class ILobby.
     */
    @Test
    public void testStopGame() throws Exception {
        System.out.println("stopGame");
        ILobby instance = new ILobbyImpl();
        instance.stopGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of restartGame method, of class ILobby.
     */
    @Test
    public void testRestartGame() throws Exception {
        System.out.println("restartGame");
        ILobby instance = new ILobbyImpl();
        instance.restartGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentHost method, of class ILobby.
     */
    @Test
    public void testGetCurrentHost() throws Exception {
        System.out.println("getCurrentHost");
        ILobby instance = new ILobbyImpl();
        String expResult = "";
        String result = instance.getCurrentHost();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLevel method, of class ILobby.
     */
    @Test
    public void testGetLevel() throws Exception {
        System.out.println("getLevel");
        ILobby instance = new ILobbyImpl();
        String expResult = "";
        String result = instance.getLevel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayers method, of class ILobby.
     */
    @Test
    public void testGetPlayers() throws Exception {
        System.out.println("getPlayers");
        ILobby instance = new ILobbyImpl();
        List<IPlayer> expResult = null;
        List<IPlayer> result = instance.getPlayers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAmountOfPlayers method, of class ILobby.
     */
    @Test
    public void testGetAmountOfPlayers() throws Exception {
        System.out.println("getAmountOfPlayers");
        ILobby instance = new ILobbyImpl();
        String expResult = "";
        String result = instance.getAmountOfPlayers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPlayer method, of class ILobby.
     */
    @Test
    public void testAddPlayer() throws Exception {
        System.out.println("addPlayer");
        String username = "";
        ILobby instance = new ILobbyImpl();
        instance.addPlayer(username);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removePlayer method, of class ILobby.
     */
    @Test
    public void testRemovePlayer() throws Exception {
        System.out.println("removePlayer");
        String username = "";
        ILobby instance = new ILobbyImpl();
        instance.removePlayer(username);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class ILobbyImpl implements ILobby {

        public void sendMessage(IMessage message) throws RemoteException {
        }

        public void updateLevel(String level) throws RemoteException {
        }

        public void toggleReadyState(String username) throws RemoteException {
        }

        public void updatePlayers() throws RemoteException {
        }

        public void updatePlayer(String username, PlayerPosition pp) throws RemoteException {
        }

        public void updateObject(int id, boolean state) throws RemoteException {
        }

        public void updateMoveableObject(int id, int dx) throws RemoteException {
        }

        public void closedGame() throws RemoteException {
        }

        public void loadedGame(String username) throws RemoteException {
        }

        public void kickPlayer(String username) throws RemoteException {
        }

        public String getName() throws RemoteException {
            return "";
        }

        public void stopGame() throws RemoteException {
        }

        public void restartGame() throws RemoteException {
        }

        public String getCurrentHost() throws RemoteException {
            return "";
        }

        public String getLevel() throws RemoteException {
            return "";
        }

        public List<IPlayer> getPlayers() throws RemoteException {
            return null;
        }

        public String getAmountOfPlayers() throws RemoteException {
            return "";
        }

        public void addPlayer(String username) throws RemoteException, CustomException {
        }

        public void removePlayer(String username) throws RemoteException {
        }

        public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        }

        public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        }

        @Override
        public void addListener(String username, RemotePropertyListener listener, String property) throws RemoteException {
        }
    }

}
