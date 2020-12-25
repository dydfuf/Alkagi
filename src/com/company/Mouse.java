package com.company;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    AlkagiPanel ap = null;

    public Mouse(AlkagiPanel _ap) {
        this.ap = _ap;
    }

    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        Al[] alBlack = ap.getAlBlack();
        Al[] alWhite = ap.getAlWhite();
        System.out.println(alBlack.length);
        System.out.println(alWhite.length);
        alWhite[0].setxSpeed(10);
        System.out.println(x + y);
    }

}
