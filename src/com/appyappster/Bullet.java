package com.appyappster;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bullet {
    String path = "./Images/";
    int x;
    int y;
    BufferedImage image1;
    int lives;

    boolean visible;


    boolean destroyed;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Bullet() {
        try {
            image1 = ImageIO.read(new File(path, "Bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x = 0;
        y = 0;
        visible = false;
    }
    public BufferedImage getImage(){
        return image1;
    }
}
