package server.game.player;

import server.game.main.GameObject;
import server.game.map.Structure;
import utils.Time;
import utils.Vector;

import java.awt.*;
import java.util.Collection;
import java.util.Locale;

public abstract class Character extends GameObject {

    protected double x, y;
    private final Rectangle hurtbox;
    private final Vector rigidbody;
    private final Vector dir;
    private double speed;
    private int dashCount = 2;
    private final int maxDash = 2;
    private final int dashSpeed = 5;
    private boolean inDash = false;
    protected boolean grounded = false;

    /**
     * 1 si il regarde à droite et -1 à gauche ça permet d'inversé pour les attaques
     */
    protected int look = 1;

    private boolean up, down, left, right = false;

    public Character(double x, double y) {
        super();
        this.x = x;
        this.y = y;
        this.hurtbox = new Rectangle((int) x, (int) y, 32, 64);
        this.rigidbody = new Vector(0, 0);
        this.dir = new Vector(0, 0);
        this.speed = 4.0;
    }

    @Override
    public void update() {
        move();
        updateHurtbox();
    }

    @Override
    public String getInfo() {
        return String.format(Locale.US, "update;character;%d;%3.2f;%3.2f;%d", getId(), x, y, look);
    }

    public void recoverDash(int count) {
        dashCount = dashCount + count > maxDash ? maxDash : dashCount + count;
    }

    private void move() {
        dir.x = (right ? 1 : 0) + (left ? -1 : 0);
        if (grounded) {
            if (dir.x > 0) setLook(1);
            else if (dir.x < 0) setLook(-1);
        }
        dir.y = (down ? 2 : 0) + (up ? -1 : 0);
    }

    private void updateHurtbox() {
        hurtbox.setLocation((int) x, (int) y);
    }

    // Commandes

    public abstract void upTilt();
    public abstract void downTilt();
    public abstract void forwardTilt();
    public abstract void jab();

    public abstract void upAir();
    public abstract void downAir();
    public abstract void forwardAir();
    public abstract void backAir();


    public void jump(Collection<Structure> structures) {
        if (grounded) {
            rigidbody.y = -10;
        }
    }

    public void dash() {
        if (dashCount > 0) {
            dashCount--;
            inDash = true;
            rigidbody.y = 0;
            Time.delay(() -> inDash = false, 100);
        }
    }

    public void moveUp() {
        down = false;
        up = true;
    }

    public void moveDown() {
        up = false;
        down = true;
    }

    public void moveLeft() {
        right = false;
        left = true;
    }

    public void moveRight() {
        left = false;
        right = true;
    }

    public void releaseUp() {
        up = false;
    }

    public void releaseDown() {
        down = false;
    }

    public void releaseLeft() {
        left = false;
    }

    public void releaseRight() {
        right = false;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector getRigidbody() {
        return rigidbody;
    }

    public Rectangle getHurtbox() {
        return hurtbox;
    }

    public Vector getDir() {
        return dir;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isInDash() {
        return inDash;
    }

    public double getDashSpeed() {
        return dashSpeed;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
        fireChangeUDP();
    }

    public void setX(double x) {
        this.x = x;
        fireChangeUDP();
    }

    public void setY(double y) {
        this.y = y;
        fireChangeUDP();
    }

    public void setLook(int look) {
        this.look = look;
        fireChange();
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    @Override
    public String toString() {
        return "Character{" +
                "rigidbody=" + rigidbody +
                ", dir=" + dir +
                ", y=" + y +
                ", x=" + x +
                '}';
    }
}
