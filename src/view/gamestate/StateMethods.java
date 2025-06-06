package view.gamestate;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface StateMethods {

    public void draw(Graphics g);

    public void mouseDragged(MouseEvent e);

    public void mouseMoved(MouseEvent e);

    public void mouseClicked(MouseEvent e);

    public void mousePressed(MouseEvent e);

    public void mouseReleased(MouseEvent e);

    public void mouseEntered(MouseEvent e);

    public void mouseExited(MouseEvent e);

    public void keyTyped(KeyEvent e);

    public void keyPressed(KeyEvent e);

    public void keyReleased(KeyEvent e);
}