package blockgames;

import map.Mapgenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer time;
    private int delay = 8;
    private int playerX = 310;
    private int ballPositionX = 200;
    private int ballPositionY = 200;
    private int ballXDirection = -1;
    private int ballYDirection = -2;
    private Mapgenerator map;

    public Gameplay() {
        map=new Mapgenerator(3,7);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();
    }

    public void paint(Graphics g) {
        // Drawing background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        //drawingmap
        map.draw((Graphics2D) g);

        // Drawing borders
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 3, 592); // Left border
        g.fillRect(0, 0, 692, 3); // Top border
        g.fillRect(689, 0, 3, 592); // Right border
        g.fillRect(0, 592, 692, 3); // Bottom border

        //scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        // Drawing the paddle
        g.setColor(Color.RED);
        g.fillRect(playerX, 550, 100, 8);

        // Drawing the ball
        g.setColor(Color. YELLOW);
        g.fillOval(ballPositionX, ballPositionY, 20, 20);
        if (totalBricks<=0){
            play=false;
            ballXDirection=0;
            ballYDirection=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You Won : ",260,300);
            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString("Press Enter to Restart ", 230, 350);
        }

        if (ballPositionY>570){
            play=false;
            ballXDirection=0;
            ballYDirection=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You have Fallen Out , Scores : ",190,300);
            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString("Press Enter to Restart ", 230, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
            // Checking collision with walls
            if (ballPositionX <= 0 || ballPositionX >= 670) {
                ballXDirection = -ballXDirection;
            }
            A:
            for (int i =0;i<map.map.length;i++){
                for (int j =0;j<map.map[0].length;j++){
                    if (map.map[i][j]>0){
                        int brickx=j*map.brickWidth+80;
                        int bricky=i*map.brickHeight+50;
                        int brickwidth=map.brickWidth;
                        int brickHeight=map.brickHeight;
                        Rectangle rect =new Rectangle(brickx, bricky, brickwidth, brickHeight);
                        Rectangle ballRect=new Rectangle(ballPositionX, ballPositionY,20,20);
                        Rectangle brickRect=rect;
                        if (ballRect.intersects(brickRect)){
                            map.setBrickvalue(0,i,j);
                            totalBricks--;
                            score+=5;
                            if (ballPositionX +19 <=brickRect.x || ballPositionX+1>=brickRect.x+brickRect.width){
                                ballXDirection=-ballXDirection;
                            }
                            else {
                                ballYDirection =-ballYDirection;
                            }
                            break A;
                        }
                    }

                }
            }
            if (ballPositionY <= 0) {
                ballYDirection = -ballYDirection;
            }

            // Checking collision with paddle
            if (new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYDirection = -ballYDirection;
            }

            // Move the ball
            ballPositionX += ballXDirection;
            ballPositionY += ballYDirection;

            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            play = !play; // Toggle play/pause
            if (!play) {
                time.stop(); // Pause the timer
            } else {
                time.start(); // Resume the timer
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            if (!play){
                play =true;
                ballPositionX=200;
                ballPositionY=200;
                ballXDirection=-1;
                ballYDirection=-2;
                playerX=310;
                score=0;
                totalBricks=21;
                map=new Mapgenerator(3,7);
                 repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    private void moveLeft() {
        play = true;
        playerX -= 20;
    }

    private void moveRight() {
        play = true;
        playerX += 20;
    }
}
