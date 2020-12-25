package com.company;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class AlkagiPanel extends JPanel implements MouseListener,
        MouseMotionListener,
        Runnable{

    private int WIDTH = 600;
    private int HEIGHT = 600;

    //my
    private Al[] alBlack = null;
    private Al[] alWhite = null;
    private BufferedImage table;
    private Image img;
    private boolean move;
    private int clickedX;
    private int clickedY;

    private Vector<Al> Als;
    private Vector<Al> Fallen;

    HashMap<String, Integer> queueLine;

    GameFrame gameFrame;

    public void setMove(boolean move) {
        this.move = move;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public Al[] getAlBlack() {
        return alBlack;
    }

    public Al[] getAlWhite() {
        return alWhite;
    }

    public AlkagiPanel(GameFrame gameFrame){
        setDoubleBuffered(true);
        /*
        try{
            img = ImageIO.read(new File("/Users/choeyonglyeol/Desktop/Alkagi/src/com/company/table.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

         */

        //setBackground
        setBackground(new Color (51, 102, 51));
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        //Add mouse listeners
        addMouseListener(this);
        addMouseMotionListener(this);

        this.gameFrame = gameFrame;

        this.Als = new Vector<>();
        this.Fallen = new Vector<>();

        populateAls();

        this.move = true;
        alBlack = new Al[5];
        alWhite = new Al[5];
        alBlack[0] = new Al(10,10,40,0,0, Color.BLACK);
        alBlack[1] = new Al(20,10,40,0,0, Color.BLACK);
        alBlack[2] = new Al(30,10,40,0,0, Color.BLACK);
        alBlack[3] = new Al(40,10,40,0,0, Color.BLACK);
        alBlack[4] = new Al(50,10,40,0,0, Color.BLACK);

        alWhite[0] = new Al(100,300,40,-1,0, Color.WHITE);
        alWhite[1] = new Al(130,300,40,0,0, Color.WHITE);
        alWhite[2] = new Al(280,300,40,0,0, Color.WHITE);
        alWhite[3] = new Al(430,300,40,0,0, Color.WHITE);
        alWhite[4] = new Al(580,300,40,0,0, Color.WHITE);

        Thread t = new Thread(this);
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();

    }

    public void populateAls() {
        Al al;

        double midy = 50;
        double firstRow = 35;

        // name, Color color, double x, double y, int speed, double direction, int size) {
        al = new Al(firstRow,50,30,0,0,Color.BLACK);
        Als.add(al);
        al = new Al(firstRow+150,midy,30,0,0,Color.BLACK);
        Als.add(al);
        al = new Al(firstRow+300,midy,30,0,0,Color.BLACK);
        Als.add(al);
        al = new Al(firstRow+450,midy,30,0,0,Color.BLACK);
        Als.add(al);
        al = new Al(firstRow+600,midy,30,0,0,Color.BLACK);
        Als.add(al);

    }
    private void paintAls(Graphics g) {
        try {
            for (Al a : Als)
                paintAl(g,a);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            paintAls(g); // retry so the disks never not get painted
        }
    }

    private void paintAl(Graphics g, Al a){
        if (a == null) return;
        int dx = (int)a.getX()+8;
        int dy = (int)a.getY()+8;
        g.setColor(a.getMyColor());
        g.fillOval((int)a.getX(),(int)a.getY(),30,30);
    }

    @Override
    public void paint(Graphics g) {
        // paint real panel stuff
        super.paint(g);
        //Make the table
        //paintTable( g );

        // paint the disks
        paintAls(g);
        paintQueueLine(g);
        //if ( aimingQueueBall ) paintQueueLine( g );
    }

    private void paintQueueLine( Graphics g) {
        g.setColor( Color.WHITE );
        if ( queueLine == null ) return;
        g.drawLine((Integer) queueLine.get("x1"),
                (Integer) queueLine.get("y1"),
                (Integer) queueLine.get("x2"),
                (Integer) queueLine.get("y2"));
    }
/*
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        alWhite[0].update();
        for(int i = 0; i < 5; i++){
            alBlack[i].draw(g);
            alWhite[i].draw(g);
        }
    }

 */


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("I clicked at " + e.getPoint());

        this.clickedX = e.getPoint().x;
        this.clickedY = e.getPoint().y;



    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(queueLine.get("x1") + " " + queueLine.get("y1") + " "  +
                queueLine.get("x2") + " "  + queueLine.get("y2"));
        queueLine = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        queueLine = new HashMap<String, Integer>();
        queueLine.put("x1", (int)StrictMath.round(this.clickedX));
        queueLine.put("y1", (int)StrictMath.round(this.clickedY));
        queueLine.put("x2", (int)StrictMath.round(mx));
        queueLine.put("y2", (int)StrictMath.round(my));

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void run() {
        // repaint every 9 ms (~100 fps)
        while (true) {
            repaint();
            try {
                Thread.sleep( 9, 1 );
            }
            catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
