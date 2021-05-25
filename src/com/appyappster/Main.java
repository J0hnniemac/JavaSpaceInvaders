package com.appyappster;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Starting Space Invaders");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        new GameFrame();
    }
}
