package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MenuPanel extends JPanel {
    private int WIDTH = 300;
    private int HEIGHT = 100;

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    JButton startButton;
    JButton optionButton;

    public MenuPanel(){
        setBorder(new TitledBorder(new LineBorder(Color.red,1)));

        startButton = new JButton("Start");
        optionButton = new JButton("Option");

        add(startButton);
        add(optionButton);
    }
}
