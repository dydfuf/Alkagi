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
        
        int i, j;
        Al black, black2, white, white2;

        for(i = 0; i < blackAls.size(); i++){
            black = blackAls.elementAt(i);
            if(black.getxSpeed() != 0 || black.getySpeed() != 0){
                black.setX(black.getX() + black.getxSpeed());
                black.setY(black.getY() + black.getySpeed());
                doFriction(black);
            }
            checkBound(black);
            for(j = i + 1; j < blackAls.size(); j++){
                for(int k = 0; k < whiteAls.size(); k++){
                    white2 = whiteAls.elementAt(k);
                    if(checkCollision(black,white2)){
                        fixOverlap(black,white2);
                        System.out.println("call setFriction(black)");
                        setFriction(black);
                        System.out.println("call setFriction(white)");
                        setFriction(white2);
                    }
                }
                black2 = blackAls.elementAt(j);
                if(checkCollision(black,black2)){
                    fixOverlap(black,black2);
                    System.out.println("call setFriction(black)");
                    setFriction(black);
                    System.out.println("call setFriction(black2)");
                    setFriction(black2);
                }
            }
        }

        for(i = 0; i < whiteAls.size(); i++){
            white = whiteAls.elementAt(i);
            if(white.getxSpeed() != 0 || white.getySpeed() != 0){
                white.setX(white.getX() + white.getxSpeed());
                white.setY(white.getY() + white.getySpeed());
                doFriction(white);
            }
            checkBound(white);

            for(j = i + 1; j < whiteAls.size(); j++){
                for(int k = 0; k < blackAls.size(); k++){
                    black2 = blackAls.elementAt(k);
                    if(checkCollision(white,black2)){
                        fixOverlap(black2,white);
                        System.out.println("call setFriction(black2)");
                        setFriction(black2);
                        System.out.println("call setFriction(white)");
                        setFriction(white);
                    }
                }
                white2 = whiteAls.elementAt(j);
                if(checkCollision(white,white2)){
                    fixOverlap(white,white2);
                    System.out.println("call setFriction(white)");
                    setFriction(white);
                    System.out.println("call setFriction(white2)");
                    setFriction(white2);
                }
            }
        }
        /*
        for(Al a : blackAls){
            if(a.getxSpeed() != 0 || a.getySpeed() != 0){
                a.setX(a.getX() + a.getxSpeed());
                a.setY(a.getY() + a.getySpeed());
                doFriction(a);
            }
            checkBound(a);
            for(Al black : blackAls){
                if(a == black)
                    break;
                for(Al white : whiteAls){
                    if(a == white)
                        break;
                    if(checkCollision(black, white)){
                        fixOverlap(black, white);
                        setFriction(black);
                        setFriction(white);
                    }
                }
            }
        }
        for(Al a : whiteAls){
            if(a.getxSpeed() != 0 || a.getySpeed() != 0){
                a.setX(a.getX() + a.getxSpeed());
                a.setY(a.getY() + a.getySpeed());
                doFriction(a);
            }
            checkBound(a);
            for(Al black : blackAls){
                if(a == black)
                    break;
                for(Al white : whiteAls){
                    if(a == white)
                        break;
                    if(checkCollision(black, white)){
                        fixOverlap(black, white);
                        setFriction(black);
                        setFriction(white);
                    }
                }
            }
        }

         */
    }

    public void checkBound(Al a){
        double xRight = ap.getWIDTH()-15;
        double yBottom = ap.getHEIGHT()-15;

        if(a.getX() > xRight){
            a.setxSpeed(-Math.abs(a.getxSpeed()));
            a.setDdx(Math.abs(a.getDdx()));
        }
        else if(a.getX() < 0){
            a.setxSpeed(Math.abs(a.getxSpeed()));
            a.setDdx(-Math.abs(a.getDdx()));
        }
        else if(a.getY() > yBottom ){
            a.setySpeed(-Math.abs(a.getySpeed()));
            a.setDdy(Math.abs(a.getDdy()));
        }
        else if(a.getY() < 0){
            a.setySpeed(Math.abs(a.getySpeed()));
            a.setDdy(-Math.abs(a.getDdy()));
        }
    }


    public void run() {
        long time;

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

    public void doFriction(Al al){

        al.setxSpeed(al.getxSpeed() + al.getDdx());
        al.setySpeed(al.getySpeed() + al.getDdy());

        if(al.getxSpeed() > 0 == al.getDdx() > 0){
            al.setxSpeed(0);
        }
        if(al.getySpeed() > 0 == al.getDdy() > 0){
            al.setySpeed(0);
        }
    }

    public void setFriction(Al al){
        System.out.println("setFriction Xspeed : " + al.getxSpeed());
        System.out.println("setFriction Yspeed : " + al.getySpeed());
        double k = (al.getxSpeed() * al.getxSpeed() + al.getySpeed() * al.getySpeed());

        k = Math.sqrt(k) * 50; //  마찰계수 120
        if(k == 0 )
            return;

        al.setDdx(-al.getxSpeed() / k);
        al.setDdy(-al.getySpeed() / k);

    }

    public boolean checkCollision(Al al1, Al al2){
        double distanceX = al1.getX() - al2.getX();
        double distanceY = al1.getY() - al2.getY();

        double distance = distanceX*distanceX + distanceY*distanceY;
        if(distance < 900){
            double kii, kji, kij, kjj;
            kji = (distanceX * al1.getxSpeed() + distanceY * al1.getySpeed()) / distance;
            kii = (distanceX * al1.getySpeed() - distanceY * al1.getxSpeed()) / distance;
            kij = (distanceX * al2.getxSpeed() + distanceY * al2.getySpeed()) / distance;
            kjj = (distanceX * al2.getySpeed() - distanceY * al2.getxSpeed()) / distance;

            al1.setxSpeed(kij * distanceX - kii * distanceY);
            al1.setySpeed(kij * distanceY + kii * distanceX);

            al2.setxSpeed(kji * distanceX - kjj * distanceY);
            al2.setySpeed(kji * distanceX - kjj * distanceY);
            return true;
        }
        return false;
    }

    public void fixOverlap(Al al1, Al al2){
        double x, y, k;

        // the real displacement from i to j
        y = (al2.getY() - al1.getY());
        x = (al2.getX() - al1.getX());

        // the ratio between what it should be and what it really is
        k = 30.1 / Math.sqrt(x * x + y * y);

        // difference between x and y component of the two vectors
        y *= (k - 1) / 2.;
        x *= (k - 1) / 2;

        // set new coordinates of disks

        al2.setY(al2.getY() + y);
        al2.setX(al2.getX() + x);
        al1.setY(al1.getY() - y);
        al1.setX(al1.getX() - x);
    }

}
