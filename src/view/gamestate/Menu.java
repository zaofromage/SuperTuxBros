package view.gamestate;

import server.host.Server;
import server.host.UDPServer;
import view.UI.Button;
import view.UI.TextInput;
import view.connection.Client;
import view.main.Game;
import view.main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Menu implements StateMethods {

    private final Game game;

    private List<Button> buttons = new ArrayList<>();
    private TextInput ip;

    public Menu(Game game) {
        this.game = game;
        buttons.add(new Button(GamePanel.dim.width/2 - 150, 200, 300, 100, Color.GREEN, "HOST", () -> {
            launchServer();
            game.switchState(GameState.SELECT);
        }));
        buttons.add(new Button(GamePanel.dim.width/2 - 150, 500, 300, 100, Color.RED, "JOIN", () -> {
            if (!ip.getText().equals("")) {
                launchClient();
                game.switchState(GameState.SELECT);
            }
        }));
        ip = new TextInput(GamePanel.dim.width/2 - 150, 350, 230, 30, "IP : ", new Font("SansSerif", Font.PLAIN, 25), 15);
    }

    @Override
    public void draw(Graphics g) {
        for (Button button : buttons) {
            button.draw(g);
        }
        ip.draw(g);
    }

    public void launchServer() {
        Server.instantiate();
        UDPServer.instantiate();
        Client.instantiate("127.0.0.1", game);
    }

    public void launchClient() {
        Client.instantiate(ip.getText(), game);
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
        ip.onClick(e);
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
        ip.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
