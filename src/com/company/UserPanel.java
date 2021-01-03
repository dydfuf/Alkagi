package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class UserPanel extends JPanel {
    private int WIDTH = 300;
    private final int HEIGHT = 300;

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    JLabel userName = new JLabel("UserName");

    public UserPanel(){
        setBorder(new TitledBorder(new LineBorder(Color.red,1)));
        this.add(userName);
    }
}
