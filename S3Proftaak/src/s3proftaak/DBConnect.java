/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Stan
 */
public class DBConnect {
    
    private Connection conn = null;
    
    public boolean connect(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proftaak", "root", "usbw");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }
    
/*
CREATE TABLE IF NOT EXISTS ACCOUNT(Username VARCHAR(20) UNIQUE, Password VARCHAR(20), Mute INTEGER(1), Fullscreen INTEGER(1), Path VARCHAR(255));
CREATE TABLE IF NOT EXISTS SCORE(Tijd INTEGER(10), Ster INTEGER(10), Usernames VARCHAR(255));
CREATE TABLE IF NOT EXISTS LOBBY(Usernames VARCHAR(255), Level VARCHAR(255));
CREATE TABLE IF NOT EXISTS LEVEL(Name VARCHAR(255));
*/
    public Settings getSettings(String username) throws SQLException{
        PreparedStatement ps = conn.prepareStatement("SELECT Mute, Fullscreen, Path FROM ACCOUNT WHERE Username = ?");
        ps.setString(1, username);
        
        ResultSet rs = ps.executeQuery();
        
        if (rs != null){
            while (rs.next()){
                return new Settings(rs.getBoolean("Mute"), rs.getBoolean("Fullscreen"), rs.getString("Path"));
            }
        }
        
        return null;
    }
    
    public void updateSettings(String username, Settings s) throws SQLException{
        PreparedStatement ps = conn.prepareStatement("UPDATE ACCOUNT SET Mute = ?, Fullscreen = ?, Path = ? WHERE Username = ?");
        ps.setBoolean(1, s.isSoundMute());
        ps.setBoolean(2, s.isFullscreen());
        ps.setString(3, s.getSkinPath());
        ps.setString(4, username);
        ps.execute();
    }
}
