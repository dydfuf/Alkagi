package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameFrame extends JFrame implements ActionListener {
    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;

    AlkagiPanel alkagiPanel;
    public GameFrame(){
        super();

        // makes window look more natural
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        setTitle("Alkagi");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setSize(WIDTH, HEIGHT);
        setResizable( false );
        setLocationByPlatform(true);
        buildMenus();


        alkagiPanel = new AlkagiPanel(this);
        add(alkagiPanel,BorderLayout.CENTER);
        requestFocus();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void buildMenus() {
        JMenuBar menubar = new JMenuBar();

        // menus
        JMenu file = new JMenu("File"),
                opts = new JMenu("Options"),
                help = new JMenu("Help");

        menubar.add(file);
        menubar.add(opts);
        menubar.add(help);

        // file menu
        //addMenuItems(file, "New Game, -, Exit Game");

        // options menu
        //addMenuItems(opts, "*Friction, *Aim Help, -, *Anti-Overlap, -");

        // help menu
        //addMenuItems(help, "About");

        // lets see it!
        setJMenuBar(menubar);
    }

    private void addMenuItems(JMenu menu, String items) {
        JMenuItem menuitem;

        // loop through items adding them to menu
        for (String s : items.split(", ")) {
            if (s.equals("-"))
                menu.addSeparator();
            else {
                if (s.substring(0, 1).equals("*")) // menu types
                    menuitem = new JCheckBoxMenuItem(s.substring(1), true);
                else if (s.substring(0, 1).equals("#"))
                    menuitem = new JCheckBoxMenuItem(s.substring(1));
                else
                    menuitem = new JMenuItem(s);

                menuitem.addActionListener(this);
                menu.add(menuitem);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        String s = e.getActionCommand();

        if (s.equals("Exit Game"))
            System.exit(0);

        else if (s.equals("New Game")) {
            System.out.println("New Game.");
            alkagiPanel.newGame();
        }

        else if (s.equals("Friction"))
            alkagiPanel.engine.toggleFriction();

        else if (s.equals("Aim Help"))
            alkagiPanel.engine.toggleAimHelp();

        else if (s.equals("Anti-Overlap"))
            alkagiPanel.engine.toggleOverLap();

        else if (s.equals("About")) // about dialog
            JOptionPane.showMessageDialog(this,
                    "Billiard Table, By: Paul George\n" +
                            "Created just for fun\n" +
                            "\n" +
                            "Monday, January 31th, 2011",
                    "About Billiard Table", JOptionPane.DEFAULT_OPTION);

        else
            System.out.println(s);
         */
    }

}
