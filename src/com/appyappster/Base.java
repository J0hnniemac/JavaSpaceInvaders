package com.appyappster;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Base {
    String path = "./Images/";
    int x;
    int y;
    BufferedImage image1;


    boolean destroyed;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Base() {
        try {
            image1 = ImageIO.read(new File(path, "Base.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x = 0;
        y = 0;

        destroyed = false;


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
