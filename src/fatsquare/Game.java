package fatsquare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Game extends JPanel implements KeyListener, ActionListener {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;
    private static int score = 0;
    private JLabel startInstruction;
    private JLabel scoreLabel;
    private Sprite player;
    private Sprite food;
    private Timer timer;
    private Direction direction;
    private boolean running;
    private Random random;
    private boolean eaten;

    Game() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setLayout(null);
        player = new Sprite(20, 430, 40, 40);
        food = new Sprite(430, 430, 40, 40);
        timer = new Timer(10, this);
        timer.start();
        direction = Direction.RIGHT;
        startInstruction = new JLabel("Press <Space> to start | Controls: Arrow Keys or VIM Keys | Press <r> to try again");
        startInstruction.setBounds(4, 875, 900, 25);
        startInstruction.setForeground(Color.WHITE);
        startInstruction.setFont(new Font("Calibri", Font.BOLD, 24));
        this.add(startInstruction);
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(4, 0, 900, 25);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        this.add(scoreLabel);
        running = false;
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // paint sprites to canvas
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.GREEN);
        g2D.fill(player);
        g2D.setPaint(Color.RED);
        g2D.fill(food);
        Toolkit.getDefaultToolkit().sync();
    }

    // keyboard controls
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K:
                direction = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J:
                direction = Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H:
                direction = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L:
                direction = Direction.RIGHT;
                break;
            case KeyEvent.VK_SPACE:
                if (score == 0) {
                    startInstruction.setText(null);
                    running = true;
                }
                break;
                // setup for new game
            case KeyEvent.VK_R:
                score = 0;
                player.x = 20;
                player.y = 430;
                player.setVelocity(6);
                player.width = 40;
                player.height = 40;
                direction = Direction.RIGHT;
                food.x = 430;
                food.y = 430;
                scoreLabel.setText("Score: " + score);
                running = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    // move player sprite
    public void movePlayer() {
        switch (direction) {
            case UP:
                player.moveUp();
                break;
            case DOWN:
                player.moveDown();
                break;
            case LEFT:
                player.moveLeft();
                break;
            case RIGHT:
                player.moveRight();
                break;
        }
    }

    public void spawnFood() {
        if (eaten) {
            random = new Random();
            // food can only spawn 80px away from border
            // food can't spawn within player
            while (player.intersects(food)) {
                int x = random.nextInt(WIDTH - 80);
                int y = random.nextInt(HEIGHT - 80);
                if (x < 80) {
                    x = x + 80; 
                } else if (y < 80) {
                    y = y + 80;
                }
                food = new Sprite(x, y, 40, 40);
            }
        } else {
            return;
        }
    }

    public void eat() {
        if (player.intersects(food)) {
            eaten = true;
            // add to score and adjust size
            player.width = player.width + 5;
            player.height = player.height + 5;
            player.x = player.x - 5;
            player.y = player.y - 5;
            score++;
            scoreLabel.setText("Score: " + score);
            // increase velocity after every 10 points
            // but start decreasing speed after score = 75
            if (score < 75 && score != 0 && score % 10 == 0) {
                player.setVelocity(player.getVelocity() + 1);
            } else if (score >= 75 && score != 0 && player.getVelocity() != 6) {
                player.setVelocity(player.getVelocity() - 1); 
            }
            return;
        }
        eaten = false;
    }

    public boolean gameOver() {
        if (player.x > WIDTH - player.width ||
                player.x < 0 ||
                player.y > HEIGHT - player.height ||
                player.y < 0) {
            scoreLabel.setText("Game Over! Score: " + score);
            return true;
        } else {
            return false;
        }
    }

    public void game() {
        // game loop
        if (running) {
            movePlayer();
            eat();
            spawnFood();
            repaint();
            // check if game is over
            if (gameOver()) {
                running = false;
            }
        }
    }

    // rerunning game() after keyboard press
    @Override
    public void actionPerformed(ActionEvent e) {
        game();
    }
}
