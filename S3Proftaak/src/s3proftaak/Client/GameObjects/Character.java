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

    private int walkingDirection, oldWalkingDirection;

    private boolean isCrouching;
    private boolean multicrouch;
    private boolean walking;

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

        this.setAnimation();
    }

    @Override
    public void update(GameContainer gc) {
        // Update player move met advanced if statement
        if ((game.isMultiplayer() && isControllabe) || (!game.isMultiplayer() && this.controlSet == 0)) {
            this.moveHorizontalMap(gc);
            this.moveVertical(gc);
        } else {
            this.moveHorizontal1(gc);
            this.moveVertical1(gc);
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
                if (game.isTextFieldEnabled()) {
                    game.sendTextFieldMessage();
                    game.isTextFieldEnabled(false);
                } else {
                    game.isTextFieldEnabled(true);
                }
            }
        }

        if (game.isMultiplayer() && isControllabe) {
            PlayerPosition pp = new PlayerPosition(this.getOffsetX() + getRect().getX(), getRect().getY(), vY, walkingDirection, oldWalkingDirection, isCrouching, isWalking());

            try {
                ClientAdministration.getInstance().getHostbackup().updatePlayer(ClientAdministration.getInstance().getAccount().getUsername(), pp);
            } catch (RemoteException ex) {
                ClientAdministration.getInstance().connectionLost();
            }
        }

        this.updateAnimation();
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
        if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
            //move map right -> x minus speed
            this.setvX(this.speed);
        } else if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
            //move map left -> x plus speed
            this.setvX(-this.speed);
        } else {
            //dont move the map
            this.setvX(0);
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

//                            if (game.isMultiplayer()) {
//                                ///ALTERED BY BERRY
//                                ((MoveableBlock) go).setDx(i);
//                                try {
//                                    ClientAdministration.getInstance().getHostbackup().updateMoveableObject(go.getId(), (int) go.getRect().getX());
//                                } catch (RemoteException ex) {
//                                    Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                                ((MoveableBlock) go).setDx((-1 * i));
//                            }
//                            ///END
                        }
                        if (getRect().getMinX() < go.getRect().getMaxX() && getRect().getMaxX() > go.getRect().getMinX()) {

                            for (int b = 1; b < 20; b++) {
                                if (this.getRect().getMinY() >= go.getRect().getMaxY() - b) {
                                    if (((MoveableBlock) go).isFalling()) {
                                        this.die();
                                    }
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
                                if (this.name.equalsIgnoreCase(ClientAdministration.getInstance().getAccount().getUsername())) {
                                    try {
                                        ClientAdministration.getInstance().getHostbackup().updateObject(go.getId(), false);
                                    } catch (RemoteException ex) {
                                        ClientAdministration.getInstance().connectionLost();
                                    }
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
                this.setvX(-this.speed);
            } else if ((controlSet == 1 && gc.getInput().isKeyDown(Input.KEY_D)) || (controlSet == 2 && gc.getInput().isKeyDown(Input.KEY_L))) {
                this.setvX(this.speed);
            } else {
                this.setvX(0);
            }
        }

        //check for collision in 5 small steps for higher precision
        float vXtemp = this.vX / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.getRect().setX(this.getRect().getX() + vXtemp);
            if (this.isColliding(gc)) {
                this.getRect().setX(this.getRect().getX() - vXtemp);
                this.setvX(0);
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
                this.setAnimation();

                if (crouching) {
                    this.getRect().setY(this.getRect().getY() + 24);
                    this.getRect().setHeight(69);
                } else {
                    this.getRect().setHeight(93);
                    this.getRect().setY(this.getRect().getY() - 24);
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
        this.multicrouch = isCrouching;
    }

    public void setvX(float vX) {
        this.vX = vX;

        if (this.vX > 0) {
            oldWalkingDirection = walkingDirection;
            walkingDirection = 1;
        }

        if (this.vX < 0) {
            oldWalkingDirection = walkingDirection;
            walkingDirection = -1;
        }

        updateAnimation();
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

    public void setOldWalkingDirection(int oldWalkingDirection) {
        this.oldWalkingDirection = oldWalkingDirection;
    }

    public void setWalkingDirection(int walkingDirection) {
        this.walkingDirection = walkingDirection;
        this.updateAnimation();
    }

    private void updateAnimation() {
        if (oldWalkingDirection != walkingDirection) {
            this.setAnimation();
        }

        if (this.isWalking() || walking) {
            if (animate.isStopped()) {
                animate.start();
            }
        } else {
            if (!animate.isStopped()) {
                animate.stop();
            }
        }
    }

    private void setAnimation() {
        try {
            playerSheet = new SpriteSheet(getClass().getResource("/Resources/Levels/player" + (controlSet + 1 < 3 ? controlSet + 1 : 3) + "_sprites" + (isCrouching ? "_crouch" : "") + (isLeft() ? "_left" : "") + ".png").getPath().replace("%20", " "), 70, !isCrouching ? 93 : 69);
            try {
                animate = new Animation(playerSheet, 100);
            } catch (ArrayIndexOutOfBoundsException x) {
            }
        } catch (SlickException ex) {
            Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    private boolean isWalking() {
        return this.vX != 0;
    }

    private boolean isLeft() {
        return walkingDirection > 0;
    }
}
