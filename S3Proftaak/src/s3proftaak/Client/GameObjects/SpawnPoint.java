/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.GameObjects;

/**
 *
 * @author Berry-PC
 */
public class SpawnPoint extends GameObject{

    public SpawnPoint(float x, float y, float width, float height) {
        super(x, y, width, height, false);
    }
        
    @Override
    public String toString(){
        return super.toString() + " -- SPAWNPOINT";
    }
    
}
