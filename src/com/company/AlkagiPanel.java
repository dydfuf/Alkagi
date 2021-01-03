package com.company;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Vector;

public class AlkagiPanel extends JPanel implements MouseListener,
        MouseMotionListener,
        Runnable{

    private int WIDTH = 700;
    private int HEIGHT = 700;

    //my
    private Al[] alBlack = null;
    private Al[] alWhite = null;
    private BufferedImage table;
    private Image img;
    private boolean move;
    private int clickedX;
    private int clickedY;
    private boolean canDrag;
    private Al clickedAl;
    private boolean turn = true; // 0 : White, 1 : Black
    private String strTurn = "Black";

    private final Vector<Al> blackAls;
    private final Vector<Al> whiteAls;
    private Vector<Al> Fallen;

    HashMap<String, Integer> queueLine;

    GameFrame gameFrame;
    AlkagiEngine alkagiEngine;

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

    public String getStrTurn() {
        return strTurn;
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
        //setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        setBorder(new TitledBorder(new LineBorder(Color.red,1)));
        setLayout(null);

        //Add mouse listeners
        addMouseListener(this);
        addMouseMotionListener(this);

        this.blackAls = new Vector<>();
        this.whiteAls = new Vector<>();
        this.Fallen = new Vector<>();
        this.gameFrame = gameFrame;
        this.alkagiEngine = new AlkagiEngine(this);

        initAls();
        alkagiEngine.setBlackAls(this.blackAls);
        alkagiEngine.setWhiteAls(this.whiteAls);

        //Start the Engine
        Thread e = new Thread(alkagiEngine);
        e.setPriority(Thread.NORM_PRIORITY);
        e.start();

        Thread t = new Thread(this);
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();

    }

    public void initAls() {
        Al al;

        double midy = 50;
        double firstRow = 35;

        // name, Color color, double x, double y, int speed, double direction, int size) {
        al = new Al(firstRow,50,30,0,0,Color.BLACK);
        blackAls.add(al);
        al = new Al(firstRow+150,midy,30,0,0,Color.BLACK);
        blackAls.add(al);
        al = new Al(firstRow+300,midy,30,0,0,Color.BLACK);
        blackAls.add(al);
        al = new Al(firstRow+450,midy,30,0,0,Color.BLACK);
        blackAls.add(al);
        al = new Al(firstRow+600,midy,30,0,0,Color.BLACK);
        blackAls.add(al);

        al = new Al(firstRow,50+500,30,0,0,Color.WHITE);
        whiteAls.add(al);
        al = new Al(firstRow+150,midy+500,30,0,0,Color.WHITE);
        whiteAls.add(al);
        al = new Al(firstRow+300,midy+500,30,0,0,Color.WHITE);
        whiteAls.add(al);
        al = new Al(firstRow+450,midy+500,30,0,0,Color.WHITE);
        whiteAls.add(al);
        al = new Al(firstRow+600,midy+500,30,0,0,Color.WHITE);
        whiteAls.add(al);

    }
    private void paintAls(Graphics g) {
        try {
            for (Al a : blackAls)
                paintAl(g,a);
            for (Al a : whiteAls)
                paintAl(g,a);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            paintAls(g); // retry so the disks never not get painted
        }
    }

    private void paintAl(Graphics g, Al a){
        if (a == null) return;
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
        if(canDrag)
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
    public void findClickedAl(int x, int y){
        for(Al a : blackAls){
            int ax = (int)StrictMath.round(a.getX());
            int ay = (int)StrictMath.round(a.getY());
            int xDif = Math.abs( ax - x );
            int yDif = Math.abs( ay - y );
            int radius = (int)StrictMath.round(a.getSize()/2);
            if ( xDif <= radius  && yDif <= radius ) {
                if(turn){
                    System.out.println(a.getX());
                    System.out.println(a.getY());
                    this.clickedAl = a;
                    break;
                }
                else{
                    System.out.println("It is White's Turn!");
                }
            }
        }
        for(Al a : whiteAls){
            int ax = (int)StrictMath.round(a.getX());
            int ay = (int)StrictMath.round(a.getY());
            int xDif = Math.abs( ax - x );
            int yDif = Math.abs( ay - y );
            int radius = (int)StrictMath.round(a.getSize()/2);
            if ( xDif <= radius  && yDif <= radius ) {
                if(!turn){
                    System.out.println(a.getX());
                    System.out.println(a.getY());
                    this.clickedAl = a;
                    break;
                }
                else{
                    System.out.println("It is Black's Turn!");
                }
            }
        }
    }

    public void toggleTurn(){
        this.turn = !turn;
        if(this.turn){
            strTurn = "Black";
        }
        else{
            strTurn = "White";
        }
        gameFrame.setTurnLabel(strTurn);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("I clicked at " + e.getPoint());

        //this.clickedX = e.getPoint().x;
        //this.clickedY = e.getPoint().y;
        findClickedAl(e.getPoint().x-15, e.getPoint().y-15);
        if(clickedAl != null){
            canDrag = true;
            this.clickedX = (int)this.clickedAl.getX()+15;
            this.clickedY = (int)this.clickedAl.getY()+15;
        }
        else{
            canDrag = false;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(queueLine.get("x1") + " " + queueLine.get("y1") + " "  +
                queueLine.get("x2") + " "  + queueLine.get("y2"));

        if(clickedAl != null){
            double x1 = e.getX();
            double y1 = e.getY();
            double x2 = clickedAl.getX()+15;
            double y2 = clickedAl.getY()+15;

            double dx = (x2 - x1)/40;
            double dy = (y2 - y1)/40;
            clickedAl.setxSpeed(dx);
            clickedAl.setySpeed(dy);
            alkagiEngine.setFriction(clickedAl);
            toggleTurn();
        }

        queueLine = null;
        this.clickedAl = null;
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
