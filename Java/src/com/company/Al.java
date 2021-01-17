package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Al {
    private double x;
    private double y;
    private int size;
    private double xSpeed = 0;
    private double ySpeed = 0;
    private double ddx;
    private double ddy;

    public double getDdx() {
        return ddx;
    }

    public void setDdx(double ddx) {
        this.ddx = ddx;
    }

    public double getDdy() {
        return ddy;
    }

    public void setDdy(double ddy) {
        this.ddy = ddy;
    }

    private Color myColor;

    private Point center;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public Al(){

    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getMyColor() {
        return myColor;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public int getSize() {
        return size;
    }

    public Al(double _x, double _y, int _size, double _xSpeed, double _ySpeed, Color _myColor){
        this.x = _x;
        this.y = _y;
        this.size = _size;
        this.xSpeed = _xSpeed;
        this.ySpeed = _ySpeed;
        this.myColor = _myColor;
        this.center = new Point();
        this.center.setLocation(_x,_y);

    }


    public void update() {
        x = x + xSpeed;
        //충돌상황
        if(x > 600 || x < 0){
            xSpeed = -xSpeed;
        }

        y = y + ySpeed;
        if(y > 600 || y < 0){
            ySpeed = -ySpeed;
        }
    }

    public void draw (Graphics g){
        g.setColor(myColor);
        g.fillOval((int)x, (int)y, size, size);
    }
}
