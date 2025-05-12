package server.game.map;

import server.game.main.GameObject;

import java.awt.*;
import java.util.Locale;

public class Structure extends GameObject {

    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle hurtbox;

    public Structure(int x, int y, int width, int height) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        hurtbox = new Rectangle(x, y, width, height);
    }

    @Override
    public void update() {

    }

    @Override
    public String getInfo() {
        return String.format(Locale.US, "update;structure;%d;%d;%d;%d;%d", getId(), x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getHurtbox() {
        return hurtbox;
    }
}
