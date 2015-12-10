/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import s3proftaak.Client.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * database inlog gegevens host: athena01.fhict.local user: dbi317440 password:
 * 31MhWIa03o
 */
/**
 * @author Stan
 */
public class DBConnect {

    private static DBConnect instance;

    public static DBConnect getInstance() {

        if (instance == null) {
            try {
                instance = new DBConnect();
                instance.connect();
            } catch (SQLException ex) {
                System.out.println(ex);
                return null;
            }
        }

        return instance;
    }

    private Connection conn = null;

    public void connect() throws SQLException {
        if (conn != null) {
            conn.close();
        }
        conn = DriverManager.getConnection("jdbc:mysql://86.94.182.242:3306/S3proftaak", "S3proftaak", "P@ssword");
        //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proftaak", "root", "usbw");
    }

    // <editor-fold defaultstate="collapsed" desc="Settings - getSettings / updateSettings"> 
    public Settings getSettings(String username) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT Mute, Fullscreen, Path FROM ACCOUNT WHERE Username = ?");
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs != null) {
            while (rs.next()) {
                return new Settings(rs.getBoolean("Mute"), rs.getBoolean("Fullscreen"), rs.getString("Path"));
            }
        }

        return null;
    }

    public void updateSettings(String username, Settings s) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE ACCOUNT SET Mute = ?, Fullscreen = ?, Path = ? WHERE Username = ?");
        ps.setBoolean(1, s.isSoundMute());
        ps.setBoolean(2, s.isFullscreen());
        ps.setString(3, s.getSkinPath());
        ps.setString(4, username);
        ps.execute();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Login - doUserLogin"> 
    public boolean doUserLogin(String username, String password) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT null FROM ACCOUNT WHERE Username = ? AND Password = ?");
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs != null) {
            while (rs.next()) {
                return true;
            }
        }

        return false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Account - getAccount / hasAccount / insertAccount"> 
    public Account getAccount(String username) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT Username, Password FROM ACCOUNT WHERE Username = ?");
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs != null) {
            while (rs.next()) {
                return new Account(rs.getString("Username"), rs.getString("Password"), this.getSettings(rs.getString("Username")));
            }
        }

        return null;
    }

    public boolean hasAccount(String username) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT Null FROM ACCOUNT WHERE Username = ?");
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs != null) {
            while (rs.next()) {
                return true;
            }
        }

        return false;
    }

    public void insertAccount(Account a) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO ACCOUNT (Username, Password, Mute, Fullscreen, Path) VALUES (?, ?, ?, ?, ?)");
        ps.setString(1, a.getUsername());
        ps.setString(2, a.getPassword());
        ps.setBoolean(3, a.getSettings().isSoundMute());
        ps.setBoolean(4, a.getSettings().isFullscreen());
        ps.setString(5, a.getSettings().getSkinPath());
        ps.execute();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Score - getScores / insertScore"> 
    public ArrayList<Score> getScores() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM SCORE");
        ResultSet rs = ps.executeQuery();

        if (rs != null) {
            ArrayList<Score> Scores = new ArrayList<>();

            while (rs.next()) {
                Scores.add(new Score(rs.getInt("Tijd"), rs.getInt("Ster"), rs.getString("Usernames"), rs.getString("Map")));
            }

            return Scores;
        }

        return null;
    }

    public void insertScore(Score s) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO SCORE (Tijd, Ster, Usernames, Map) VALUES (?, ?, ?, ?)");
        ps.setInt(1, s.getTime());
        ps.setInt(2, s.getAmountOfStars());
        ps.setString(3, s.getPlayerNames());
        ps.setString(4, s.getMap());
        ps.execute();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Level - getLevelName"> 
    public String getLevelName(int id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT Name FROM LEVEL WHERE Id = ?");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs != null) {
            while (rs.next()) {
                return rs.getString("Name");
            }
        }

        return null;
    }
    // </editor-fold>
}
