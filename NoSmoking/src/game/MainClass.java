package game;

import javax.swing.JFrame;

public class MainClass extends JFrame {

    GamePlay gamePlay;

    public MainClass() {
        setTitle("Lung Breaker");
        setSize(900, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePlay = new GamePlay();
        add(gamePlay);
    }

    public static void main(String[] args) {
        MainClass frame = new MainClass();
        frame.setVisible(true);
    }
}
