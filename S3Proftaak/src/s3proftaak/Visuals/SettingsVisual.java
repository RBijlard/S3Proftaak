/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Visuals;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 *
 * @author Stan
 */
public class SettingsVisual extends BasicScene {

    public SettingsVisual(){
        Group root = new Group();
 
        Label l = new Label("Sound");
        
        ToggleButton tb1 = new ToggleButton("On");
        tb1.setId("on");
        ToggleButton tb2 = new ToggleButton("Off");
        tb2.setId("off");
 
        ToggleGroup group = new ToggleGroup();
        tb1.setToggleGroup(group);
        tb2.setToggleGroup(group);
        group.selectToggle(tb1);
 
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(l, tb1, tb2);
        root.getChildren().add(hBox);
        
        setScene(new Scene(root));
    }
}
