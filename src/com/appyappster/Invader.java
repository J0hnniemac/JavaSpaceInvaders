package com.appyappster;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Invader {
    String path = "./Images/";
    int x;
    int y;
    BufferedImage image1;
    BufferedImage image2;
    int tick;
    boolean destroyed;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Invader(String img1,String img2)  {
        try {
            image1 = ImageIO.read(new File(path, img1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            image2 = ImageIO.read(new File(path, img2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x = 0;
        y = 0;
        tick = 0;
        destroyed = false;
    }

    public void tickTock(){
        tick++;
        if(tick>1) {
            tick = 0;
        }

    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public BufferedImage getImage(){

        if(tick == 0) {
            return image1;
        }else {
            return image2;
        }
    }

}
