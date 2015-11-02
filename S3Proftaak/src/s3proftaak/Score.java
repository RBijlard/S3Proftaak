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
public class Score {
    private int time;
    private int amountOfStars;
    private String playerNames;

    public Score(int time, int amountOfStars, String playerNames) {
        this.time = time;
        this.amountOfStars = amountOfStars;
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

    public void setAmountOfStars() {
        this.amountOfStars = amountOfStars;
    }

    public String getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(String playerNames) {
        this.playerNames = playerNames;
    }
    
    @Override
    public String toString(){
        return getTime() + " " + getAmountOfStars() + " " + getPlayerNames();
    }
}
