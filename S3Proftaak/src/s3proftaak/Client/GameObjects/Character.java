package s3proftaak.Client.GameObjects;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.Client.Game;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IUpdateable;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.SoundManager;
import s3proftaak.Client.SoundManager.Sounds;
import s3proftaak.Shared.PlayerPosition;

/**
 *
 * @author Berry-PC
 */
public class Character extends GameObject implements IRenderable, IUpdateable {

    private final String name;
    private final boolean isControllabe;

    private float gravity = 0.5f;
    private float jumpStrength = -12;
    private float speed = 4;
    private int interations = 5;
    private float vX = 0;
    private float vY = 0;
    private final int controlSet;

    private Game game;
    private SpriteSheet playerSheet;
    private Animation animate;
    private GameObject MLO;
    private float offSetX;

    private boolean isCrouching;
    private boolean multicrouch;

    float marginy, marginx;

    public Character(Game game, float x, float y, float width, float height, int controlSet, String name) throws SlickException {
        super(x, y, width, height);
        this.name = name;

        this.isControllabe = this.name.equals(ClientAdministration.getInstance().getAccount().getUsername());

        this.game = game;
        this.controlSet = controlSet;

        MLO = new Block(1f, 1f, 1f, 1f);
        this.game.getGameObjects().add(MLO);
        for (GameObject go : this.game.getGameObjects()) {
            if (go.getRect().getX() < MLO.getRect().getX()) {
                MLO.getRect().setX(go.getRect().getX());
            }
        }
        marginx = 0 - MLO.getRect().getX();

        this.isCrouching = false;

        try {
            playerSheet = new SpriteSheet(getClass().getResource("/Resources/Levels/player" + (controlSet + 1 < 3 ? controlSet + 1 : 3) + "_sprites.png").getPath().replace("%20", " "), 70, 93);
            animate = new Animation(playerSheet, 100);
        } catch (SlickException ex) {
            Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(GameContainer gc) {
        //update player (move)

        if (game.isMultiplayer()) {
            if (isControllabe) {
                this.moveHorizontalMap(gc);
                this.moveVertical(gc);
            } else {
                this.moveHorizontal1(gc);
                this.moveVertical1(gc);
            }

        } else {
            switch (this.controlSet) {
                case 0:
                    this.moveHorizontalMap(gc);
                    this.moveVertical(gc);
                    break;
                case 1:
                case 2:
                    this.moveHorizontal1(gc);
                    this.moveVertical1(gc);
                    break;
            }
        }

        if (!game.isMultiplayer()) {
            if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
                try {
                    SoundManager.getInstance().restartSound();
                    ClientAdministration.getInstance().getApp().reinit();
                } catch (SlickException ex) {
                    Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (game.isMultiplayer()) {
            if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
                Game game = ClientAdministration.getInstance().getGame();

                if (game.isTextFieldEnabled()) {
                    game.sendTextFieldMessage();
                    game.isTextFieldEnabled(false);
                } else {
                    game.isTextFieldEnabled(true);
                }
            }
        }

        if (game.isMultiplayer() && isControllabe) {
            PlayerPosition pp = new PlayerPosition(this.getOffsetX() + getRect().getX(), getRect().getY(), vY, isCrouching);

            try {
                ClientAdministration.getInstance().getHostbackup().updatePlayer(ClientAdministration.getInstance().getAccount().getUsername(), pp);
            } catch (RemoteException ex) {
                ClientAdministration.getInstance().connectionLost();
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        //render animation
        animate.draw(this.getRect().getX(), this.getRect().getY());

        if (game.isMultiplayer()) {
            //Draw Username Above Character
            g.setColor(Color.yellow);
            g.setFont(game.getSlickFontUsername());
            g.drawString(name, this.getRect().getX() + 35 - (g.getFont().getWidth(name) / 2), this.getRect().getY() - 25);
        }
    }

    public void moveHorizontalMap(GameContainer gc) {

        this.offSetX = 0 - MLO.getRect().getX();
        //Move horizontal with arrow keys
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_LEFT)) {
            //move map right -> x minus speed
            this.vX = this.speed;
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            //move map left -> x plus speed
            this.vX = -this.speed;
        } else {
            //dont move the map
            this.vX = 0;
        }

        //check collisions
        float vXtemp = this.vX / this.interations;
        for (int i = 0; i < this.interations; i++) {
            for (GameObject go : game.getGameObjects()) {
                if (go != this) {
                    go.getRect().setX(go.getRect().getX() + vXtemp);
                }
            }
            if (this.isColliding(gc)) {
                for (GameObject go : game.getGameObjects()) {
                    if (go != this) {
                        go.getRect().setX(go.getRect().getX() - vXtemp);
                    }
                }
            }
        }
    }

    public float getOffsetX() {
        return this.offSetX - this.marginx;
    }

    public void moveHorizontal(GameContainer gc) {
        //move with arrow keys
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_LEFT)) {
            //move leftt -> x min
            this.vX = -this.speed;
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            //move right -> x plus
            this.vX = this.speed;
        } else {
            this.vX = 0;
        }

        //check for collision in 5 small steps for higher precision
        float vXtemp = this.vX / this.interations;
        for (int i = 0; i < this.interations; i++) {
            //ipv setx -> render map
            this.getRect().setX(this.getRect().getX() + vXtemp);
            if (this.isColliding(gc)) {
                //ipv setx -> render map 
                this.getRect().setX(this.getRect().getX() - vXtemp);
                this.vX = 0;
            }
        }
    }

    public void moveVertical(GameContainer gc) {
        //move with arrow keys
        Input input = gc.getInput();
        this.vY += this.gravity;
        if (input.isKeyDown(Input.KEY_UP)) {
            //move up -> y min
            //ipv sety -> render map
            this.getRect().setY(this.getRect().getY() + 0.1f);
            if (this.isColliding(gc) && !isObjectAbove()) {
                this.vY = this.jumpStrength;
                SoundManager.getInstance().playSound(Sounds.JUMP);
            }
            this.getRect().setY(this.getRect().getY() - 0.1f);
        }

        this.checkCrouch(input.isKeyDown(Input.KEY_DOWN) || multicrouch);

        if (this.getRect().getY() > (70 * 15)) {
            this.die();
        }

        //check for collision in 5 small steps for higher precision
        float vYtemp = this.vY / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.getRect().setY(this.getRect().getY() + vYtemp);
            if (this.isColliding(gc)) {
                this.getRect().setY(this.getRect().getY() - vYtemp);
                this.vY = 0;
            }
        }
    }

    public void die() {
        if (!game.isMultiplayer()) {
            try {
                SoundManager.getInstance().restartSound();
                ClientAdministration.getInstance().getApp().reinit();

            } catch (SlickException ex) {
                Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (getName().equals(ClientAdministration.getInstance().getAccount().getUsername())) {
                try {
                    ClientAdministration.getInstance().getCurrentLobby().restartGame();
                } catch (RemoteException ex) {
                    ClientAdministration.getInstance().connectionLost();
                }
            }
        }
    }

    public boolean isColliding(GameContainer gc) {
        Rectangle rect = this.getRect();
        for (GameObject go : game.getGameObjects()) {
            //check if colliding
            if (go.getRect().intersects(rect) || go.getRect().contains(rect)) {
                if (go != this) {
                    //check what object
                    if (go instanceof Block || go instanceof Character) {
                        return true;
                    } else if (go instanceof MoveableBlock) {
                        if (go.getRect().getMinY() + 1 < rect.getMaxY()) {
                            int i = 0;
                            if (go.getRect().getX() > rect.getX()) {
                                i = 1;
                                if (game.isMultiplayer()) {
                                    try {
                                        ClientAdministration.getInstance().getHostbackup().updateMoveableObject(go.getId(), i);
                                    } catch (RemoteException ex) {
                                        ClientAdministration.getInstance().connectionLost();
                                    }
                                }
                            }
                            if (go.getRect().getX() < rect.getX()) {
                                i = -1;
                                if (game.isMultiplayer()) {
                                    try {
                                        ClientAdministration.getInstance().getHostbackup().updateMoveableObject(go.getId(), i);
                                    } catch (RemoteException ex) {
                                        ClientAdministration.getInstance().connectionLost();
                                    }
                                }
                            }
                            if (!game.isMultiplayer()) {
                                ((MoveableBlock) go).setDx(i);
                            }

                            ///ALTERED BY BERRY
//                            ((MoveableBlock) go).setDx(i);
//                            try {
//                                ClientAdministration.getInstance().getHostbackup().updateMoveableObject(go.getId(), (int)go.getRect().getX());
//                            } catch (RemoteException ex) {
//                                Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
//                            }
                            ///END
                        }
                        if (getRect().getMinX() < go.getRect().getMaxX() && getRect().getMaxX() > go.getRect().getMinX()) {

                            for (int b = 1; b < 20; b++) {
                                if (this.getRect().getMinY() >= go.getRect().getMaxY() - b) {
                                    this.die();
                                    return true;
                                }
                            }
                        }

                        return true;
                    } else if (go instanceof Spike) {
                        this.die();
                        return false;
                    } else if (go instanceof Button) {
                        if (this.getRect().getMinY() - 1 < go.getRect().getY()) {
                            if (!((Button) go).isActive()) {
                                ((Button) go).setActive(true);

                                if (game.isMultiplayer()) {
                                    try {
                                        ClientAdministration.getInstance().getHostbackup().updateObject(go.getId(), true);
                                    } catch (RemoteException ex) {
                                        ClientAdministration.getInstance().connectionLost();
                                    }
                                }
                            }
                        }
                        return true;
                    } else if (go instanceof Lever && gc.getInput().isKeyPressed(Input.KEY_E)) {
                        if (!((Lever) go).isActive()) {
                            ((Lever) go).setActive(true);
                        } else {
                            ((Lever) go).setActive(false);
                        }

                        if (game.isMultiplayer()) {
                            try {
                                ClientAdministration.getInstance().getHostbackup().updateObject(go.getId(), ((Lever) go).isActive());
                            } catch (RemoteException ex) {
                                ClientAdministration.getInstance().connectionLost();
                            }
                        }
                    } else if (go instanceof Door) {
                        if (this.getRect().getX() < go.getRect().getX() && this.getRect().getX() + this.getRect().getWidth() > go.getRect().getX() + go.getRect().getWidth()) {
                            System.out.println(((Door) go).isActive());
                            if (((Door) go).isActive()) {
                                System.out.println("finish");
                                ((Door) go).finish();
                                try {
                                    ClientAdministration.getInstance().getCurrentLobby().stopGame();
                                } catch (RemoteException ex) {
                                    ClientAdministration.getInstance().connectionLost();
                                }
                            }
                        }
                    } else if (go instanceof Star) {
                        if (!((Star) go).isRemoved()) {
                            ((Star) go).setActive(false);

                            if (game.isMultiplayer()) {
                                try {
                                    ClientAdministration.getInstance().getHostbackup().updateObject(go.getId(), false);
                                } catch (RemoteException ex) {
                                    ClientAdministration.getInstance().connectionLost();
                                }
                            }
                        }
                    } else if (go instanceof Weight) {
                        if (getRect().getMinX() < go.getRect().getMaxX() && getRect().getMaxX() > go.getRect().getMinX()) {
                            if (this.getRect().getMinY() >= go.getRect().getMaxY() - 5) {
                                if (!((Weight) go).isActive()) {
                                    this.die();
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + " -- CHARACTER";
    }

    public void moveHorizontal1(GameContainer gc) {
        if (!game.isMultiplayer()) {
            if ((controlSet == 1 && gc.getInput().isKeyDown(Input.KEY_A)) || (controlSet == 2 && gc.getInput().isKeyDown(Input.KEY_J))) {
                this.vX = -this.speed;
            } else if ((controlSet == 1 && gc.getInput().isKeyDown(Input.KEY_D)) || (controlSet == 2 && gc.getInput().isKeyDown(Input.KEY_L))) {
                this.vX = this.speed;
            } else {
                this.vX = 0;
            }
        }

        //check for collision in 5 small steps for higher precision
        float vXtemp = this.vX / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.getRect().setX(this.getRect().getX() + vXtemp);
            if (this.isColliding(gc)) {
                this.getRect().setX(this.getRect().getX() - vXtemp);
                this.vX = 0;
            }
        }
    }

    public void moveVertical1(GameContainer gc) {
        if (!game.isMultiplayer()) {
            this.vY += this.gravity;
            if ((controlSet == 1 && gc.getInput().isKeyDown(Input.KEY_W)) || (controlSet == 2 && gc.getInput().isKeyDown(Input.KEY_I))) {
                this.getRect().setY(this.getRect().getY() + 0.1f);
                if (this.isColliding(gc) && !isObjectAbove()) {
                    this.vY = this.jumpStrength;
                    SoundManager.getInstance().playSound(Sounds.JUMP);
                }
                this.getRect().setY(this.getRect().getY() - 0.1f);
            }

            if ((controlSet == 1 && gc.getInput().isKeyDown(Input.KEY_S)) || (controlSet == 2 && gc.getInput().isKeyDown(Input.KEY_K))) {
                this.checkCrouch(true);
            } else {
                this.checkCrouch(false);
            }

        } else {
            this.checkCrouch(this.multicrouch);
        }

        if (this.getRect().getY() > (70 * 15)) {
            this.die();
        }

        //check for collision in 5 small steps for higher precision
        float vYtemp = this.vY / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.getRect().setY(this.getRect().getY() + vYtemp);
            if (this.isColliding(gc)) {
                this.getRect().setY(this.getRect().getY() - vYtemp);
                this.vY = 0;
            }
        }
    }

    private void checkCrouch(boolean crouching) {
        if (isCrouching != crouching) {
            if (!crouching) {
                if (isObjectAbove()) {
                    return;
                }
            }

            isCrouching = crouching;

            try {
                playerSheet = new SpriteSheet(getClass().getResource("/Resources/Levels/player" + (controlSet + 1 < 3 ? controlSet + 1 : 3) + "_sprites" + (crouching ? "_crouch" : "") + ".png").getPath().replace("%20", " "), 70, !crouching ? 93 : 69);
                animate = new Animation(playerSheet, 100);

                if (crouching) {
                    this.getRect().setHeight(69);
                    this.getRect().setY(this.getRect().getY() + 24);
                } else {
                    this.getRect().setY(this.getRect().getY() - 24);
                    this.getRect().setHeight(93);
                }

            } catch (Exception ex) {
                Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean isObjectAbove() {
        for (GameObject go : game.getGameObjects()) {
            if (!(go instanceof Door) && !(go instanceof Star) && !(go instanceof Lever)) {
                if (getRect().getMinX() <= go.getRect().getMaxX() && getRect().getMaxX() >= go.getRect().getMinX()) {
                    if (getRect().getMinY() - 23 <= go.getRect().getMaxY() && getRect().getMaxY() > go.getRect().getMaxY()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public String getName() {
        return this.name;
    }

    public void setIsCrouching(boolean isCrouching) {
        //this.isCrouching = isCrouching;
        this.multicrouch = isCrouching;
    }

    public void setvX(float vX) {
        this.vX = vX;
    }

    public void setvY(float vY) {
        this.vY = vY;
    }

    public boolean safeMoveTo(float x, float y) {
        GameObject tempGo = new Block(x, y, getRect().getWidth(), getRect().getHeight());

        for (GameObject go : game.getGameObjects()) {
            //check if colliding
            if (go.getRect().intersects(tempGo.getRect()) || go.getRect().contains(tempGo.getRect())) {
                if (go != this) {
                    //check what object
                    if (go instanceof MoveableBlock || go instanceof Character) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
