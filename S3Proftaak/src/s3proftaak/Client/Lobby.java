/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.util.ArrayList;

/**
 *
 * @author S33D
 */
public class Lobby {
    private final int id;
    private String level;
    private ArrayList<Account> players;

    public Lobby(int id, String level, ArrayList<Account> players) {
        this.id = id;
        this.level = level;
        this.players = players;
    }
    
    public int getId(){
        return this.id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public ArrayList<Account> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Account> players) {
        this.players = players;
    }
}
