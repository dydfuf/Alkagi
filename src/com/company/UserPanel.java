package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class UserPanel extends JPanel {
    private final int WIDTH = 300;
    private final int HEIGHT = 250;

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    JLabel userName;
    JLabel winRate;
    JLabel win_lose;

    public UserPanel(){
        setLayout(null);
        setBorder(new TitledBorder(new LineBorder(Color.red,1)));

        userName = new JLabel("UserName");
        userName.setBounds(110,35,190,30);
        this.add(userName);

        winRate = new JLabel("Win Rate N%");
        winRate.setBounds(110,65,190,30);
        this.add(winRate);

        win_lose = new JLabel("N승 N패");
        win_lose.setBounds(110,95,190,30);
        this.add(win_lose);
    }
}
