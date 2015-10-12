/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Stan
 */
public class DBConnect {
    
    private static Connection conn = null;
    
    private boolean connect(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c2j", "root", "usbw");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }
    
    private void refreshTable() throws SQLException{
        Statement st = conn.createStatement();
        st.execute("DROP TABLE IF EXISTS HIGHSCORE");
        st.execute("CREATE TABLE IF NOT EXISTS HIGHSCORE(Name VARCHAR(25), Score INT)");
        
        insertScore("Stan", 25000);
    }
    
    private void insertScore(String name, int score) throws SQLException{
        conn.createStatement().execute("INSERT INTO HIGHSCORE(Name, Score) VALUES('" + name + "', " + score + ")");
    }
    
    private void getScores() throws SQLException{
        ResultSet rs = conn.prepareStatement("SELECT * FROM HIGHSCORE").executeQuery();
        
        while (rs.next()) {
                System.out.print(rs.getString(1));
                System.out.print(": ");
                System.out.println(rs.getInt(2));
        }
    }
}
