/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals.Lobby_Utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Stan
 */
public class LocalPlayer {

    private final String name;
    private final BooleanProperty ready;

    public LocalPlayer(String name, boolean ready) {
        this.name = name;
        this.ready = new SimpleBooleanProperty(ready);
    }

    public String getName() {
        return name;
    }

    public BooleanProperty readyProperty() {
        return ready;
    }
}
