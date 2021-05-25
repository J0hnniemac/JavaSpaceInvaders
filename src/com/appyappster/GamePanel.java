package com.appyappster;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH) / UNIT_SIZE;
    static final int DELAY = 20;

    static final int LEFT_MARGIN = 72;
    static final int HORIZONTAL_SPACER = 32;
    static final int Y_START = 32;
    static final int Y_SPACER = 32;
    static final int RIGHT_X_LIMIT = 420;
    static final int LEFT_X_LIMIT = 72;


    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';

    boolean running = false;

    javax.swing.Timer timer;
    Random random;


    //Invaders  11 across
    Invader[] invaderTopRows;
    Invader[] invaderSecondRows;
    Invader[] invaderThirdRows;
    Invader[] invaderFourthRows;
    Invader[] invaderFifthRows;
    Base[] bases;
    Player player;
    Bullet bullet;

    int currentY = Y_START;

    long lastTime1;
    long lastTime2;
    long lastTime3;
    long lastTime4;
    long lastTime5;

    Timer offsetTimer;
    TimerTask task;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new DimensionUIResource(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        //inv = new InvaderTopRow();
        invaderTopRows = new Invader[11];
        invaderSecondRows = new Invader[11];
        invaderThirdRows = new Invader[11];
        invaderFourthRows = new Invader[11];
        invaderFifthRows = new Invader[11];
        bases = new Base[4];
        player = new Player();


        initInvaders();
        initBases();
        initPlayer();
        lastTime1 = System.currentTimeMillis();
        lastTime2 = System.currentTimeMillis();

        offsetTimer = new Timer();

        bullet = new Bullet();
        startGame();

    }

    private void initPlayer() {
        System.out.printf("initPlayer");
        player.setX(LEFT_MARGIN);
        player.setY(330);
    }

    private void initBases() {
        System.out.printf("initBases");
        for (int i = 0; i < bases.length; i++) {
            bases[i] = new Base();
            bases[i].x = ((i * (HORIZONTAL_SPACER * 3)) + LEFT_MARGIN + HORIZONTAL_SPACER);
            bases[i].y = 300;
        }


    }

    private void initInvaders() {
        System.out.printf("initInvaders");
        for (int i = 0; i < invaderTopRows.length; i++) {
            invaderTopRows[i] = new Invader("inv11.png", "inv12.png");
            System.out.printf("i:%d \n", i);
            invaderTopRows[i].setX((i * HORIZONTAL_SPACER) + LEFT_MARGIN);
            invaderTopRows[i].setY(currentY);
        }

        for (int i = 0; i < invaderSecondRows.length; i++) {
            invaderSecondRows[i] = new Invader("inv21.png", "inv22.png");
            System.out.printf("i:%d \n", i);
            invaderSecondRows[i].setX((i * HORIZONTAL_SPACER) + LEFT_MARGIN);
            invaderSecondRows[i].setY(currentY + Y_SPACER);
        }

        for (int i = 0; i < invaderThirdRows.length; i++) {
            invaderThirdRows[i] = new Invader("inv21.png", "inv22.png");
            System.out.printf("i:%d \n", i);
            invaderThirdRows[i].setX((i * HORIZONTAL_SPACER) + LEFT_MARGIN);
            invaderThirdRows[i].setY(currentY + (Y_SPACER * 2));
        }
        for (int i = 0; i < invaderFourthRows.length; i++) {
            invaderFourthRows[i] = new Invader("inv31.png", "inv32.png");
            System.out.printf("i:%d \n", i);
            invaderFourthRows[i].setX((i * HORIZONTAL_SPACER) + LEFT_MARGIN);
            invaderFourthRows[i].setY(currentY + (Y_SPACER * 3));
        }
        for (int i = 0; i < invaderFifthRows.length; i++) {
            invaderFifthRows[i] = new Invader("inv31.png", "inv32.png");
            System.out.printf("i:%d \n", i);
            invaderFifthRows[i].setX((i * HORIZONTAL_SPACER) + LEFT_MARGIN);
            invaderFifthRows[i].setY(currentY + (Y_SPACER * 4));
        }


    }

    public void startGame() {

        running = true;
        timer = new javax.swing.Timer(DELAY, this);
        timer.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);


    }

    public void draw(Graphics g) {
        //System.out.println("paint");
        g.setColor(Color.green);

        g.drawLine(0, 16, SCREEN_WIDTH, 16);
        g.drawLine(0, 400, SCREEN_WIDTH, 400);

        g.drawLine(64, 16, 64, 400);
        g.drawLine(464, 16, 464, 400);


        drawTopRow(g);
        drawSecondRow(g);
        drawThirdRow(g);
        drawFourthRow(g);
        drawFifthRow(g);

        drawBases(g);
        drawPlayer(g);
        drawBullet(g);


    }

    private void drawBullet(Graphics g) {

        g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), 3, 3, null);
    }

    private void drawPlayer(Graphics g) {
        g.drawImage(player.getImage(), player.getX(), player.getY(), 32, 16, null);
        for (Base b : bases) {
            g.drawImage(b.getImage(), b.getX(), b.getY(), 32, 16, null);
        }
    }

    private void drawBases(Graphics g) {
        for (Base b : bases) {

            g.drawImage(b.getImage(), b.getX(), b.getY(), 32, 16, null);
        }

    }

    private void drawTopRow(Graphics g) {
        for (Invader inv : invaderTopRows) {
            if (inv.destroyed == false) {
                g.drawImage(inv.getImage(), inv.getX(), inv.getY(), 32, 16, null);
            }
        }
    }

    private void drawSecondRow(Graphics g) {
        for (Invader inv : invaderSecondRows) {
            if (inv.destroyed == false) {
                g.drawImage(inv.getImage(), inv.getX(), inv.getY(), 32, 16, null);
            }
        }
    }

    private void drawThirdRow(Graphics g) {
        for (Invader inv : invaderThirdRows) {
            if (inv.destroyed == false) {
                g.drawImage(inv.getImage(), inv.getX(), inv.getY(), 32, 16, null);
            }
        }
    }

    private void drawFourthRow(Graphics g) {
        for (Invader inv : invaderFourthRows) {
            if (inv.destroyed == false) {
                g.drawImage(inv.getImage(), inv.getX(), inv.getY(), 32, 16, null);
            }
        }
    }

    private void drawFifthRow(Graphics g) {
        for (Invader inv : invaderFifthRows) {
            if (inv.destroyed == false) {
                g.drawImage(inv.getImage(), inv.getX(), inv.getY(), 32, 16, null);
            }
        }
    }

    private void directionCheck() {
        int leftCheck = 999;
        int rightCheck = 0;
        for (Invader inv : invaderTopRows) {
            if (inv.x < leftCheck && !inv.destroyed) {
                leftCheck = inv.x;
            }
            if (inv.x > rightCheck && !inv.destroyed) {
                rightCheck = inv.x;
            }
        }
        for (Invader inv : invaderSecondRows) {
            if (inv.x < leftCheck && !inv.destroyed) {
                leftCheck = inv.x;
            }
            if (inv.x > rightCheck && !inv.destroyed) {
                rightCheck = inv.x;
            }
        }
        for (Invader inv : invaderThirdRows) {
            if (inv.x < leftCheck && !inv.destroyed) {
                leftCheck = inv.x;
            }
            if (inv.x > rightCheck && !inv.destroyed) {
                rightCheck = inv.x;
            }
        }
        for (Invader inv : invaderFourthRows) {
            if (inv.x < leftCheck && !inv.destroyed) {
                leftCheck = inv.x;
            }
            if (inv.x > rightCheck && !inv.destroyed) {
                rightCheck = inv.x;
            }
        }
        for (Invader inv : invaderFifthRows) {
            if (inv.x < leftCheck && !inv.destroyed) {
                leftCheck = inv.x;
            }
            if (inv.x > rightCheck && !inv.destroyed) {
                rightCheck = inv.x;
            }
        }

        if (rightCheck > RIGHT_X_LIMIT) {
            direction = 'L';
        }
        if (leftCheck < LEFT_X_LIMIT) {
            direction = 'R';
        }


    }

    private void tickTopRow() {
        for (Invader inv : invaderTopRows) {
            inv.tickTock();

            switch (direction) {
                case 'L':
                    inv.x = inv.x - 2;
                    break;
                case 'R':
                    inv.x = inv.x + 2;
                    break;

                default:
                    break;
            }


        }
    }

    private void tickSecondRow() {
        for (Invader inv : invaderSecondRows) {
            inv.tickTock();
            switch (direction) {
                case 'L':
                    inv.x = inv.x - 2;
                    break;
                case 'R':
                    inv.x = inv.x + 2;
                    break;

                default:
                    break;
            }
        }
    }

    private void tickThirdRow() {
        for (Invader inv : invaderThirdRows) {
            inv.tickTock();
            switch (direction) {
                case 'L':
                    inv.x = inv.x - 2;
                    break;
                case 'R':
                    inv.x = inv.x + 2;
                    break;

                default:
                    break;
            }
        }
    }

    private void tickFourthRow() {
        for (Invader inv : invaderFourthRows) {
            inv.tickTock();
            switch (direction) {
                case 'L':
                    inv.x = inv.x - 2;
                    break;
                case 'R':
                    inv.x = inv.x + 2;
                    break;

                default:
                    break;
            }
        }
    }

    private void tickFifthRow() {
        for (Invader inv : invaderFifthRows) {
            inv.tickTock();
            switch (direction) {
                case 'L':
                    inv.x = inv.x - 2;
                    break;
                case 'R':
                    inv.x = inv.x + 2;
                    break;

                default:
                    break;
            }
        }
    }


    public void checkCollisions() {

        //iterate each visible invader and check position against bullet
        for (Invader inv : invaderFifthRows) {
            if (inv.destroyed == false) {
                if (checkInvBullet(inv.x, inv.y, bullet.x, bullet.y)) {
                    System.out.println("HIT Fifth Row");
                    inv.destroyed = true;
                    resetBullet();
                }
            }
        }
        for (Invader inv : invaderFourthRows) {
            if (inv.destroyed == false) {
                if (checkInvBullet(inv.x, inv.y, bullet.x, bullet.y)) {
                    System.out.println("HIT Fourth Row");
                    inv.destroyed = true;
                    resetBullet();
                }
            }
        }
        for (Invader inv : invaderThirdRows) {
            if (inv.destroyed == false) {
                if (checkInvBullet(inv.x, inv.y, bullet.x, bullet.y)) {
                    System.out.println("HIT Third Row");
                    inv.destroyed = true;
                    resetBullet();
                }
            }
        }
        for (Invader inv : invaderSecondRows) {
            if (inv.destroyed == false) {
                if (checkInvBullet(inv.x, inv.y, bullet.x, bullet.y)) {
                    System.out.println("HIT Second Row");
                    inv.destroyed = true;
                    resetBullet();
                }
            }
        }
        for (Invader inv : invaderTopRows) {
            if (inv.destroyed == false) {
                if (checkInvBullet(inv.x, inv.y, bullet.x, bullet.y)) {
                    System.out.println("HIT First Row");
                    inv.destroyed = true;
                    resetBullet();
                }
            }
        }
    }

    public boolean checkInvBullet(Integer x1, Integer y1, Integer x2, Integer y2) {

        boolean hit = false;
        if (x2 > x1 && x2 < x1 + 32) {
            System.out.println("X HIT Row");

            if (y2 > y1 && y2 < y1 + 32) {
                System.out.println("Y HIT  ");
                hit = true;
            }
        }
        return hit;
    }

    public void gameOver(Graphics g) {

    }


    private void movePlayerLeft() {
        if (player.getX() > LEFT_MARGIN) {
            player.setX(player.getX() - 4);
        }

    }

    private void movePlayerRight() {
        if (player.getX() < RIGHT_X_LIMIT) {
            player.setX(player.getX() + 4);
        }

    }

    private void moveBullet() {
        if (bullet.y < -1) {
            bullet.visible = false;
        } else {
            //move up
            bullet.y = bullet.y - 3;

        }
    }

    private void resetBullet() {

        bullet.x = -4;
        bullet.y = -4;
        bullet.visible = false;
    }


    private void fireBullet() {
        if (!bullet.visible) {
            bullet.x = player.getX() + 16;
            bullet.y = player.getY() - 4;
            bullet.visible = true;
        }


    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent k) {
            switch (k.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    movePlayerLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    movePlayerRight();
                    break;
                case KeyEvent.VK_SPACE:
                    fireBullet();
                    break;
            }

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {

            long deltaTime1 = System.currentTimeMillis() - lastTime1;

            if (deltaTime1 > 500) {
                directionCheck();
                tickTopRow();
                //tickSecondRow();
                tickThirdRow();
                //tickFourthRow();
                tickFifthRow();
                lastTime1 = System.currentTimeMillis();
                task = new TimerTask() {
                    public void run() {
                        //System.out.println("Inside Timer Task" + System.currentTimeMillis());
                        tickOtherRows();
                    }
                };
                offsetTimer.schedule(task, 100);
            }

            moveBullet();
            checkCollisions();
        }
        repaint();
    }

    void tickOtherRows() {
//        tickTopRow();
        tickSecondRow();
//        tickThirdRow();
        tickFourthRow();
//        tickFifthRow();

    }
}
