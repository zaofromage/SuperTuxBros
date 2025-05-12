package view.main;

import view.input.KeyboardInput;
import view.input.MouseInput;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    //public static final Dimension dim = new Dimension(1920, 1080);
    public static final Dimension dim = new Dimension(500, 500);

    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPreferredSize(dim);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(new KeyboardInput(game));
        MouseInput mouseInput = new MouseInput(game);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
        Toolkit.getDefaultToolkit().sync();
    }
}
