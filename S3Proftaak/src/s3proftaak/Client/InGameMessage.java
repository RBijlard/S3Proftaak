/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import s3proftaak.Shared.IMessage;

/**
 *
 * @author Roel
 */
public class InGameMessage {

    private TrueTypeFont chatFont;
    private List<IMessage> gameMessages;

    public InGameMessage() {
        this.chatFont = new TrueTypeFont(new Font("Montserrat", Font.PLAIN, 16), false);
        this.gameMessages = new ArrayList<IMessage>();
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
        for (IMessage im : this.gameMessages) {
            grp.drawString(im.toString(), 1, hgt - 320 + count);
            count += grp.getFont().getHeight(im.toString()) + 2;
        }
    }
}
