package view.input;


import view.main.Game;
import view.gamestate.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {

    private Game game;

    public MouseInput(Game game) {
        this.game = game;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                game.getMenu().mouseDragged(e);
                break;
            case PLAYING:
                game.getPlaying().mouseDragged(e);
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                game.getMenu().mouseMoved(e);
                break;
            case PLAYING:
                game.getPlaying().mouseMoved(e);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                game.getMenu().mouseClicked(e);
                break;
            case PLAYING:
                game.getPlaying().mouseClicked(e);
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                game.getMenu().mousePressed(e);
                break;
            case PLAYING:
                game.getPlaying().mousePressed(e);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                game.getMenu().mouseReleased(e);
                break;
            case PLAYING:
                game.getPlaying().mouseReleased(e);
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                game.getMenu().mouseEntered(e);
                break;
            case PLAYING:
                game.getPlaying().mouseEntered(e);
                break;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                game.getMenu().mouseExited(e);
                break;
            case PLAYING:
                game.getPlaying().mouseExited(e);
                break;
        }
    }

}