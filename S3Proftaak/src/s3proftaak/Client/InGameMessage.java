/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import s3proftaak.Shared.IMessage;

/**
 *
 * @author Roel
 */
public class InGameMessage {

    private final TrueTypeFont chatFont;
    private final List<IMessage> gameMessages;

    public InGameMessage() {
        this.chatFont = new TrueTypeFont(new Font("Montserrat", Font.PLAIN, 16), false);
        this.gameMessages = new ArrayList<>();
    }

    public void addMessage(IMessage m) {
        if (!this.gameMessages.contains(m)) {
            if (this.gameMessages.size() > 9) {
                this.gameMessages.remove(0);
            }
            this.gameMessages.add(m);
        }
    }

    public void draw(Graphics grp, int hgt) {
        int count = 0;
        grp.setFont(chatFont);
        
        List<IMessage> tempMessages = new ArrayList<>();
        tempMessages.addAll(this.gameMessages);
        
        Collections.reverse(tempMessages);
        
        for (IMessage im : tempMessages) {
            if (im.getTime() > System.currentTimeMillis() || ClientAdministration.getInstance().getGame().isTextFieldEnabled()){
                grp.drawString(im.toString(), 1, hgt - ((grp.getFont().getHeight(im.getMessage()) + 4) * count++));
            }
        }
    }
}
