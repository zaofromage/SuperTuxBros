package view.main;

import java.awt.*;

public abstract class ViewObject {

    private int id;

    public ViewObject(int id) {
        this.id = id;
    }

    public abstract void draw(Graphics g);

    public int getId() {
        return id;
    }

}
