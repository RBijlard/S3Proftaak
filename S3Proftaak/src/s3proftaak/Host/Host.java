/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Host;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import s3proftaak.Server.Player;
import s3proftaak.Shared.IHost;
import s3proftaak.Shared.IMessage;
import s3proftaak.Shared.Wrappers.MoveableBlockPosition;
import s3proftaak.Shared.Wrappers.PlayerPosition;
import s3proftaak.fontys.BasicPublisher;
import s3proftaak.fontys.RemotePropertyListener;
import s3proftaak.util.ICare;

/**
 *
 * @author Berry-PC
 */
public class Host extends UnicastRemoteObject implements IHost, ICare {

    private static Remote getInstance() {
        return instance;
    }

    private final List<Player> players = new ArrayList<>();
    private final BasicPublisher publisher;
    private static Host instance;

    @Override
    public void playerLostConnection(String playerName) {
        // BERRY, vul hier in wat er moet gebeuren wanneer 1 speler connectie verliest
        // dus game afsluite en weet ik ut wat nog meer
        // Moet trouwens ook nog als game is afgelope terug met de Server connecte, anders krijg je Connection Lost zoals nu want er is geen verbinding
        // meer met server door deze host ^^

        //YO, player connection lost: game stopt -> players naar lobby, de player zonder connection wordt gekickt.
        //JA, connection van de server moet opnieuw opgezet worden wanneer de game gefinished wordt..
        stopGame();
    }

    public Host(String ipAddress) throws RemoteException {
        instance = (Host) this;
        this.publisher = new BasicPublisher(this, new String[]{"Administrative", "Chat", "Level", "Players", "Rect", "Host", "Objects"});
    }

    @Override
    public void sendMessage(IMessage message) {
        publisher.inform(this, "Chat", null, message);
    }

    @Override
    public void updatePlayer(String username, PlayerPosition pp) {
        publisher.inform(this, "Rect", username, pp);
    }

    @Override
    public void updateObject(int id, boolean state) throws RemoteException {
        publisher.inform(this, "Objects", id, state);
    }

    @Override
    public void updateMoveableObject(int id, MoveableBlockPosition mb) throws RemoteException {
        publisher.inform(this, "Objects", id, mb);
    }

    @Override
    public void addListener(String username, RemotePropertyListener listener, String property) throws RemoteException {
        System.out.println("username added to listeners:  " + username + ", to property:  " + property);
        publisher.addListener(username, listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }

    @Override
    public void stopGame() {
        for (Player p : players) {
            p.setReady(false);
        }

        publisher.inform(this, "Administrative", "StopGame", null);
    }
}
