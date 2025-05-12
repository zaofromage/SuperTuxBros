package server.game.player;

import server.game.main.GameObject;
import server.game.map.Structure;
import utils.Vector;

import java.awt.*;
import java.util.Locale;
import java.util.Map;

public class Player extends GameObject {

    private String name;
    private double x, y;
    private Rectangle hurtbox;
    private Vector rigidbody;
    private Vector dir;
    private double speed;

    private boolean up, down, left, right = false;

    // Collision
    private Map<Integer, Structure> structures;

    public Player(String name, double x, double y, Map<Integer, Structure> structures) {
        super();
        this.name = name;
        this.x = x;
        this.y = y;
        this.hurtbox = new Rectangle((int) x, (int) y, 32, 64);
        this.rigidbody = new Vector(0, 0);
        this.dir = new Vector(0, 0);
        this.speed = 4.0;
        this.structures = structures;
    }

    @Override
    public void update() {
        move();
        applyMove();
        applyGravity();
    }

    @Override
    public String getInfo() {
        return String.format(Locale.US, "update;player;%d;%s;%3.2f;%3.2f", getId(), name, x, y);
    }

    private void applyMove() {

        double dx = getX() + rigidbody.x + dir.x*speed;
        double dy = getY() + rigidbody.y + dir.y*speed;

        Structure collideX = null;
        Structure collideY = null;

        if (structures.isEmpty() && (dx != 0 && dy != 0)) {
            set(dx, dy);
        } else {
            for (Structure s : structures.values()) {
                if (s.getHurtbox().intersects(new Rectangle((int)dx, (int)y, hurtbox.width, hurtbox.height))) {
                    collideX = s;
                }
                if (s.getHurtbox().intersects(new Rectangle((int)x, (int)dy, hurtbox.width, hurtbox.height))) {
                    collideY = s;
                }
            }
            if (collideX == null && collideY == null) {
                set(dx, dy);
            } else if (collideX == null && collideY != null) {
                rigidbody.y = 0;
                set(dx, collideY.getY() - hurtbox.height);
            } else if (collideX != null && collideY == null) {
                rigidbody.x = 0;
                set(collideX.getX() + hurtbox.width, dy);
            }
        }
    }

    private void applyGravity() {
        if (rigidbody.y < 10) {
            rigidbody.y += 0.5;
        }
    }

    private void move() {
        dir.x = (right ? 1 : 0) + (left ? -1 : 0);
        dir.y = (down ? 1 : 0) + (up ? -1 : 0);
    }


    // Commandes
    public void jump() {
        rigidbody.y -= 10;
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

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setName(String name) {
        this.name = name;
        fireChange();
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

    @Override
    public String toString() {
        return "Player{" +
                "rigidbody=" + rigidbody +
                ", dir=" + dir +
                ", name='" + name + '\'' +
                ", y=" + y +
                ", x=" + x +
                '}';
    }
}
