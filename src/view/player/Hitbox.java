package view.player;

import view.main.ViewObject;

import java.awt.*;

public class Hitbox extends ViewObject {

    private Rectangle hitbox;

    public Hitbox(int id, Rectangle hitbox) {
        super(id);
        this.hitbox = hitbox;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(255, 0, 0, 125));
        g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public void set(int x, int y, int width, int height) {
        hitbox.setBounds(x, y, width, height);
    }
}
