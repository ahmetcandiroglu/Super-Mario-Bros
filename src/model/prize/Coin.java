package model.prize;

import model.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Coin extends GameObject implements Prize{

    private int point;

    private boolean revealed;

    public Coin(double x, double y, BufferedImage style, int point){
        super(x, y, style);
        this.point = point;
        revealed = false;
        setDimension(30, 42);
    }

    @Override
    public int getPoint() {
        return point;
    }

    @Override
    public void updateLocation(){}

    @Override
    public void draw(Graphics g){
        if(revealed){
            g.drawImage(getStyle(), (int)getX(), (int)getY(), null);
        }
    }
}
