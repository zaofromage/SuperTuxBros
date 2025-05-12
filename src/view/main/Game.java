package view.main;

import view.gamestate.GameState;
import view.gamestate.Menu;
import view.gamestate.Playing;

import java.awt.Graphics;

public class Game implements Runnable {

    private static final int FPS = 120;

    private GamePanel panel;
    private GameWindow window;
    private Thread gameLoop;

    // Game States
    private Menu menu;
    private Playing playing;

    public Game() {
        panel = new GamePanel(this);
        window = new GameWindow(panel);
        panel.requestFocus();
        menu = new Menu(this);
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
        }
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
                menu = new Menu(this);
                GameState.state = GameState.MENU;
            }
            case PLAYING -> {
                menu = null;
                playing = new Playing();
                GameState.state = GameState.PLAYING;
            }
        }
    }

    public Menu getMenu() { return menu; }

    public Playing getPlaying() {
        return playing;
    }
}
