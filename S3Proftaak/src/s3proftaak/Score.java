/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

/**
 *
 * @author S33D
 */
public class Score implements Comparable<Score>{
    private int time;
    private int amountOfStars;
    private String playerNames, gamename;

    public Score(int time, int amountOfStars, String playerNames, String gamename) {
        this.time = time;
        this.amountOfStars = amountOfStars;
        this.gamename = gamename;
        this.playerNames = playerNames;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getAmountOfStars() {
        return amountOfStars;
    }

    public void setAmountOfStars(int amountOfStars) {
        this.amountOfStars = amountOfStars;
    }

    public String getPlayerNames() {
        return playerNames;
    }
    
    public String getMap() {
        return gamename;
    }

    public void setPlayerNames(String playerNames) {
        this.playerNames = playerNames;
    }
    
    @Override
    public String toString(){
        return getTime() + " " + getAmountOfStars() + " " + getPlayerNames() + " " + this.gamename + " " +  (10000 - (getTime()/100) + (getAmountOfStars() * 100));
    }

    @Override
    public int compareTo(Score t) {
        return this.getTime() - t.getTime();
    }
}
