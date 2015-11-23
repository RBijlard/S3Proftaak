/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

/**
 *
 * @author Stan
 */
public interface IServer {
    public ILobby joinLobby(String username, String lobbyname);
    public void leaveLobby(String username);
    public ILobby createLobby(String username, String lobbyname);
    public void sendMessage(String username, Message message);
    public void ready(String username);
    public void unready(String username);
    public void move(int userid, int x, int y, int crouch);
    public void interact(int objectid, int state);
}
