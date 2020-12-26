package com.company;

import java.util.Vector;

public class AlkagiEngine implements Runnable {

    private final AlkagiPanel ap;
    private Vector<Al> blackAls;
    private Vector<Al> whiteAls;

    public void setWhiteAls(Vector<Al> whiteAls) {
        this.whiteAls = whiteAls;
    }

    public void setBlackAls(Vector<Al> blackAls) {
        this.blackAls = blackAls;
    }

    // options
    boolean overlap = true, // stop disks from overlapping eachother
            friction = true,
            aimHelp = true;

    public AlkagiEngine(AlkagiPanel _ap) {
        this.ap = _ap;
    }

    public void calcPositions() {
        for(Al a : blackAls){
            if(a.getxSpeed() != 0 || a.getySpeed() != 0){
                System.out.println("a x" + a.getX());
                System.out.println("a y" + a.getY());
                System.out.println("a x speed" + a.getxSpeed());
                System.out.println("a y speed" + a.getySpeed());
                a.setX(a.getX() + a.getxSpeed());
                a.setY(a.getY() + a.getySpeed());
                a.setxSpeed(a.getxSpeed()/1.05);
                a.setySpeed(a.getySpeed()/1.05);
            }
            checkBound(a);
            if(a.getxSpeed() < 0.01 && a.getySpeed() < 0.01){
                a.setxSpeed(0);
                a.setySpeed(0);
            }
        }
        for(Al a : whiteAls){
            if(a.getxSpeed() != 0 || a.getySpeed() != 0){
                System.out.println("a x" + a.getX());
                System.out.println("a y" + a.getY());
                System.out.println("a x speed" + a.getxSpeed());
                System.out.println("a y speed" + a.getySpeed());
                a.setX(a.getX() + a.getxSpeed());
                a.setY(a.getY() + a.getySpeed());
                a.setxSpeed(a.getxSpeed()/1.05);
                a.setySpeed(a.getySpeed()/1.05);
            }
            checkBound(a);
            if(a.getxSpeed() < 0.01 && a.getySpeed() < 0.01){
                a.setxSpeed(0);
                a.setySpeed(0);
            }

        }

    }

    public void checkBound(Al a){
        double xRight = ap.getWIDTH()-15;
        double yBottom = ap.getHEIGHT()-15;

        if(a.getX() > xRight){
            a.setxSpeed(-Math.abs(a.getxSpeed()));
        }
        else if(a.getX() < 0){
            a.setxSpeed(Math.abs(a.getxSpeed()));
        }
        else if(a.getY() > yBottom ){
            a.setySpeed(-Math.abs(a.getySpeed()));
        }
        else if(a.getY() < 0){
            a.setySpeed(Math.abs(a.getySpeed()));
        }
    }


    public void run() {
        long time;
        int i = 0;
        while (true) {
            time = System.currentTimeMillis();
            calcPositions();
            time = System.currentTimeMillis() - time;

            // sleep for 6 seconds in total. subtract calculations time
            time = 6 - time;

            // so sleep is never passed a negitive time
            if (time < 0)
                time = 0;

            try {
                Thread.sleep(time, 1);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
