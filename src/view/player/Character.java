package view.player;

import view.main.ViewObject;

import java.awt.*;

public class Character extends ViewObject {

    private double x, y;
    private int look;

    public Character(int id, double x, double y, int look) {
        super(id);
        this.x = x;
        this.y = y;
        this.look = look;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int)x, (int)y, 32, 64);
    }

    public void set(double x, double y, int look) {
        this.x = x;
        this.y = y;
        this.look = look;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + getId() +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
