package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ChatPanel extends JPanel {
    private final int WIDTH = 300;
    private final int HEIGHT = 100;

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    JLabel chat;

    public ChatPanel(){
        setBorder(new TitledBorder(new LineBorder(Color.red,1)));

        chat = new JLabel("this is Chat");

        add(chat);
    }
}
