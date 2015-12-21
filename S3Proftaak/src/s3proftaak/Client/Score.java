/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

/**
 *
 * @author S33D
 */
public class Score {
    private final int time;
    private final int amountOfStars;
    private final String playerNames;
    private final String gamename;
    private final int totalScore;

    public Score(int time, int amountOfStars, String playerNames, String gamename) {
        this.time = time;
        this.amountOfStars = amountOfStars;
        this.gamename = gamename;
        this.playerNames = playerNames;
        this.totalScore = (10000 - (time/100) + (amountOfStars * 100));
    }

    public int getTime() {
        return time;
    }

    public int getAmountOfStars() {
        return amountOfStars;
    }

    public String getPlayerNames() {
        return playerNames;
    }

    public String getGamename() {
        return gamename;
    }

    public int getTotalScore() {
        return totalScore;
    }
    
    @Override
    public String toString(){
        return getTime() + " " + getAmountOfStars() + " " + getPlayerNames() + " " + this.gamename + " " + this.totalScore;
    }
}
