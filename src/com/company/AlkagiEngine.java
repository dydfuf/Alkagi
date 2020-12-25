package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

public class AlkagiEngine implements Runnable {

    private AlkagiPanel ap;
    private Vector<Al> als;

    public void setAls(Vector<Al> als) {
        this.als = als;
    }

    // options
    boolean overlap  = true, // stop disks from overlapping eachother
            friction = true,
            aimHelp  = true;

    int i;

    public AlkagiEngine(AlkagiPanel _ap){
        this.ap = _ap;
    }

    public void run(){
        long time;
        int i = 0;
        while(true){
            ap.repaint();
            try{
                Thread.sleep(10);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
