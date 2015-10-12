/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import java.util.ArrayList;

/**
 *
 * @author S33D
 */
public class Lobby {
    private final int id;
    private String level;
    private ArrayList<Deelnemer> players;

    public Lobby(int id, String level, ArrayList<Deelnemer> players) {
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

    public ArrayList<Deelnemer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Deelnemer> players) {
        this.players = players;
    }
}
