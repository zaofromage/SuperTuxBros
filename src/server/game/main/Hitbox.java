package server.game.main;

import server.game.player.Character;
import utils.Time;
import utils.Vector;

import java.awt.*;
import java.util.Locale;

public class Hitbox extends GameObject {

    private final Rectangle hitbox;
    private Vector dir;

    private Character immune;

    public Hitbox(Rectangle hitbox, Vector dir, Character immune, int lifetime) {
        this.hitbox = hitbox;
        this.dir = dir;
        this.immune = immune;
        Time.delay(() -> this.destroy(), lifetime);
    }

    @Override
    public void update() {

    }

    @Override
    public String getInfo() {
        return String.format(Locale.US, "update;hitbox;%d;%d;%d;%d;%d", getId(), (int)hitbox.getX(), (int)hitbox.getY(), (int)hitbox.getWidth(), (int)hitbox.getHeight());
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public Vector getDir() {
        return dir;
    }

    public Character getImmune() {
        return immune;
    }

    @Override
    public String toString() {
        return "Hitbox{" +
                "hitbox=" + hitbox +
                ", dir=" + dir +
                ", immune=" + immune +
                '}';
    }
}
