package view.gamestate;

import view.UI.Button;
import view.connection.Client;
import view.main.Game;
import view.main.GamePanel;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SelectCharacter implements StateMethods {

    private final Game game;

    private List<Button> buttons = new ArrayList<>();

    public SelectCharacter(Game game) {
        this.game = game;
        buttons.add(new Button(GamePanel.dim.width - 350, GamePanel.dim.height - 150, 300, 100, Color.GREEN, "LAUNCH", () -> {
            game.switchState(GameState.PLAYING);
            Client.getInstance().getSender().send("launch;");
        }));
        buttons.add(new Button(GamePanel.dim.width / 2, GamePanel.dim.height/2, 100, 100, Color.PINK, "TUX", () -> {
            Client.getInstance().getSender().send("create;player;tux");
        }));
    }

    @Override
    public void draw(Graphics g) {
        for (Button button : buttons) {
            button.draw(g);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Button button : buttons) {
            button.onClick(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
