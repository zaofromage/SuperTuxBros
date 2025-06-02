package view.main;

import java.awt.Graphics;

public abstract class ViewObject {

    private int id;
    private boolean destroyed = false;

    public ViewObject(int id) {
        this.id = id;
    }

    public abstract void draw(Graphics g);

    public int getId() {
        return id;
    }

    public void destroy() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
