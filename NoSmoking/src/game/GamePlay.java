package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements ActionListener, KeyListener {
    boolean play = false; //game on
    boolean paused = false;
    int totalBricks; //bricks left in game
    Timer timer;
    int ballposX;
    int ballposY;
    int ballXdir; //velocities of ball
    int ballYdir;
    int playerX; //position of player paddle
    int playerY;
    MapGenerator map;
    int score;
    int paddleWidth; //dimensions of paddle
    int paddleHeight;

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(1, this);
        timer.start();

        ballposX = 350;
        ballposY = 700;
        
//        Random random = new Random();
//        ballXdir = random.nextInt(11) - 5; 
//        ballYdir = ballXdir - 1;
        
        ballXdir = -2;
        ballYdir = -4;
        playerX = 350;
        playerY = 740;
        paddleWidth = 120;
        paddleHeight = 15;

        map = new MapGenerator();
        totalBricks = map.bricksCount;
        score = 0;
    }

    public void paint(Graphics g) {
        // black canvas
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 900, 800);

        // paddle
        g.setColor(Color.WHITE);
        g.fillRect(playerX, playerY, paddleWidth, paddleHeight);
        g.setColor(new Color(203, 157, 6));
        g.fillRect(playerX, playerY, (paddleWidth * 7) / 20, paddleHeight);

        // bricks
        map.draw((Graphics2D) g);

        // ball
        g.setColor(Color.WHITE);
        g.fillOval(ballposX, ballposY, 15, 15);

        // score
        g.setColor(Color.CYAN);
        g.setFont(new Font("Verdana", Font.BOLD, 20));
        g.drawString("Score: " + score, 750, 30);

        // damage percent
        g.setColor(Color.GREEN);
        g.fillRect(700, 50, 150, 10);
        g.setColor(Color.RED);
        g.fillRect(700, 50, ((map.bricksCount - totalBricks) * 300) / map.bricksCount, 10);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD, 15));
        g.drawString("Health Bar", 750, 80);

        // "Smoking Kills!" message
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString("Smoking Kills!", 10, 30);

        // Game over message
        if (ballposY >= playerY + 25) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.BLUE);
            g.setFont(new Font("Times New Roman", Font.BOLD, 45));
            g.drawString("Smoking quitted! Total damage - " + score, 125, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to restart", 280, 350);
        }

        // Winning message
        if (totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 40));
            g.drawString("You won!", 350, 300);

            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press Enter to restart", 280, 350);
        }
        
        // Paused message
        if (paused) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("serif", Font.BOLD, 40));
            g.drawString("Paused", 380, 300);
        }
    }

    void moveLeft() {
        play = true;
        playerX -= 25;
        playerX = Math.max(0, playerX);
    }

    void moveRight() {
        play = true;
        playerX += 25;
        playerX = Math.min(850, playerX);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play && !paused) { // Check if not paused
            if (ballposX <= 0) {
                ballXdir *= -1;
            }

            if (ballposX >= 875) {
                ballXdir *= -1;
            }

            if (ballposY <= 0) {
                ballYdir *= -1;
            }

            Rectangle ballRect = new Rectangle(ballposX, ballposY, 15, 15); //rectangle near about size of ball
            Rectangle paddleRect = new Rectangle(playerX, playerY, paddleWidth, paddleHeight);

            if (ballRect.intersects(paddleRect)) { //change direction
                ballYdir *= -1;
            }

            boolean collisionDetected = false; // Flag to track collision detection

            for (int i = 0; i < map.map.length && !collisionDetected; ++i) {
                for (int j = 0; j < map.map[0].length && !collisionDetected; ++j) {
                    if (map.map[i][j] > 0) {
                        int width = map.brickWidth;
                        int height = map.brickHeight;
                        int brickXpos = map.xOffset + j * width;
                        int brickYpos = map.yOffset + i * height;

                        Rectangle brickRect = new Rectangle(brickXpos, brickYpos, width, height);

                        if (ballRect.intersects(brickRect)) {
                            if (map.map[i][j] == 2) { //pink lungs
                                map.setBrick(1, i, j);
                            } else if (map.map[i][j] == 1) { //brown lungs
                                map.setBrick(0, i, j);
                                totalBricks--;
                            }
                            score++;

                            if (ballposX + 14 <= brickXpos || ballposX + 1 >= brickXpos + width) { // left or right collision
                                ballXdir *= -1;
                            } else {
                                ballYdir *= -1;
                            }

                            collisionDetected = true; // collision detected
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft();
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight();
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER && !play) {
            score = 0;
            totalBricks = map.bricksCount;
            ballposX = 350;
            ballposY = 700;
            ballXdir = -2;
            ballYdir = -4;
            playerX = 320;
            map = new MapGenerator();
            paused = false; //game should not be paused when restarted
        }
        
        // Pause or resume game when 'P' is pressed
        if (e.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused; // Toggle paused state
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}

