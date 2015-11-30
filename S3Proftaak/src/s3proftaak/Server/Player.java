/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Server;

/**
 *
 * @author Stan
 */
public class Player {
    
    public String name;
    public boolean ready;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public boolean isReady() {
        return ready;
    }

    public void toggleReady() {
        this.ready = !ready;
    }
}
