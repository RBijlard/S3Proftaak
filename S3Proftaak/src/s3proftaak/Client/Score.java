/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.text.SimpleDateFormat;

/**
 *
 * @author S33D
 */
public class Score {
    private final int time;
    private final String timeString;
    private final int amountOfStars;
    private final String playerNames;
    private final String gamename;
    private final int totalScore;

    public Score(int time, int amountOfStars, String playerNames, String gamename) {
        this.time = time;
        this.timeString = new SimpleDateFormat("mm:ss:SS").format(time);
        this.amountOfStars = amountOfStars;
        this.gamename = gamename;
        this.playerNames = playerNames;
        this.totalScore = (10000 - (time/100) + (amountOfStars * 100));
    }
    
    public int getTime(){
        return time;
    }

    public String getTimeString() {
        return timeString;
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
}
