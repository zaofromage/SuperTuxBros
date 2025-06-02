package view.UI;

import view.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class PopUpMenu {

    protected int x, y, width, height;
    private Color backgroundColor;
    protected List<Button> buttons = new ArrayList<>();
    protected int previousLength;

    public PopUpMenu(int x, int y, int width, int height, Color bcolor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.backgroundColor = bcolor;
        previousLength = buttons.size();
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.dim.width, GamePanel.dim.height);
        g.setColor(backgroundColor);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.setColor(Color.black);
        for (Button b : buttons) {
            b.draw(g);
        }
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void mouseClicked(MouseEvent e) {
        for (Button b : buttons) {
            b.onClick(e);
        }
    }

    public void keyPressed(KeyEvent e) {

    }
}
