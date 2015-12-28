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
public class Account {

    private String username;
    private String password;
    private Settings settings;

    public Account(String username, String password, Settings settings) {
        this.username = username;
        this.password = password;
        this.settings = settings == null ? new Settings() : settings;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setSettingsinAdmin() {
        
    }
}
