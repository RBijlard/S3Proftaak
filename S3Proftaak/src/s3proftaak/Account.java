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
public class Account {
    
    private String username;
    private String password;
    
    public Account(String username, String password){
        this.username = username;
        this.password = password;
        //check gegevens vanuit database klasse.. (iteratie 2)
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
}
