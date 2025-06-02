package view.player;

import view.main.ViewObject;

import java.awt.*;

public class Player extends ViewObject {

    private double x, y;
    private String name;
    private int look;

    public Player(int id, String name, double x, double y, int look) {
        super(id);
        this.name = name;
        this.x = x;
        this.y = y;
        this.look = look;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString(name, (int) x, (int) y);
        g.fillRect((int)x, (int)y, 32, 64);
    }

    public void set(String name, double x, double y, int look) {
        this.name = name;
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
                ", name='" + name + '\'' +
                '}';
    }
}
