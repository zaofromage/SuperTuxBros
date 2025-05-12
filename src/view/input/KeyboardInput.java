package view.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.main.Game;
import view.gamestate.GameState;

public class KeyboardInput implements KeyListener {

    private Game game;

    public KeyboardInput(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                game.getMenu().keyTyped(e);
                break;
            case PLAYING:
                game.getPlaying().keyTyped(e);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                game.getMenu().keyPressed(e);
                break;
            case PLAYING:
                game.getPlaying().keyPressed(e);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                game.getMenu().keyReleased(e);
                break;
            case PLAYING:
                game.getPlaying().keyReleased(e);
                break;
        }
    }

}
