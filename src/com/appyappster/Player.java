package com.appyappster;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
    String path = "./Images/";
    int x;
    int y;
    BufferedImage image1;
    int lives;


    boolean destroyed;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLives() {
        return lives;
    }

    public Player() {
        try {
            image1 = ImageIO.read(new File(path, "Player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x = 0;
        y = 0;

        destroyed = false;
        lives = 3;


    }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getImage(){
        return image1;
    }



}
