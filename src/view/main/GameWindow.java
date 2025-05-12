package view.main;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel panel) {
        setTitle("Super Tux Bros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        setResizable(false);
        pack();
        setVisible(true);
    }
}
