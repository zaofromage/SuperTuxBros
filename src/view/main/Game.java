package view.main;

import utils.Time;
import view.gamestate.GameState;
import view.gamestate.Menu;
import view.gamestate.Playing;
import view.gamestate.SelectCharacter;
import view.player.Character;
import view.player.Player;

import java.awt.*;
import java.util.Map;

public class Game implements Runnable {

    private static final int FPS = 120;

    private GamePanel panel;
    private GameWindow window;
    private Thread gameLoop;

    private Map<Integer, Player> players;

    private Player player;

    private static String logMessage = "";

    // Game States
    private Menu menu;
    private Playing playing;
    private SelectCharacter select;

    public Game() {
        menu = new Menu(this);
        panel = new GamePanel(this);
        window = new GameWindow(panel);
        panel.requestFocus();
        this.startGameLoop();
    }

    private void startGameLoop() {
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case MENU -> menu.draw(g);
            case PLAYING -> playing.draw(g);
            case SELECT -> select.draw(g);
        }
        g.setColor(Color.black);
        g.drawString(logMessage, 10, 50);
    }

    public static void log(String logMessage) {
        Game.logMessage = logMessage;
    }

    @Override
    public void run() {
        double timePerTick = 1000000000.0 / FPS;

        long previousTime = System.nanoTime();
        int frames = 0;

        long lastTime = System.nanoTime();
        double delta = 0;

        while (true) {
            long now = System.nanoTime();

            delta += (now - previousTime) / timePerTick;
            previousTime = now;
            if (delta >= 1) {
                panel.repaint();
                frames++;
                delta--;
            }

            if (System.currentTimeMillis() - lastTime >= 1000) {
                lastTime = System.currentTimeMillis();
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    public void switchState(GameState state) {
        switch (state) {
            case MENU -> {
                playing = null;
                select = null;
                menu = new Menu(this);
                GameState.state = GameState.MENU;
            }
            case PLAYING -> {
                menu = null;
                select = null;
                playing = new Playing(player);
                GameState.state = GameState.PLAYING;
            }
            case SELECT -> {
                menu = null;
                playing = null;
                select = new SelectCharacter(this);
                GameState.state = GameState.SELECT;
            }
        }
    }

    public Menu getMenu() { return menu; }

    public Playing getPlaying() {
        return playing;
    }

    public SelectCharacter getSelect() { return select; }

    public Map<Integer, Player> getPlayers() { return players; }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
