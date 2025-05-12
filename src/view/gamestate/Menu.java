package view.gamestate;

import server.host.Server;
import server.host.UDPServer;
import view.connection.Client;
import view.main.Game;
import view.main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Menu implements StateMethods {

    private final Game game;
    private final String ip = "192.168.1.106";


    public Menu(Game game) {
        this.game = game;
    }

    @Override
    public void draw(Graphics g) {
        g.drawString("menu", 100, 100);
    }

    public void launchServer() {
        Server.instantiate();
        UDPServer.instantiate();
        Client.instantiate("127.0.0.1", game);
    }

    public void launchClient() {
        Client.instantiate(ip, game);
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
        if (e.getX() < GamePanel.dim.width / 2) {
            launchServer();
        } else {
            launchClient();
        }
        game.switchState(GameState.PLAYING);
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
