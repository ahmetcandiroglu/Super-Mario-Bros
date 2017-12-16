package model.prize;

import manager.GameEngine;
import manager.MapManager;
import model.GameObject;
import model.hero.Mario;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BoostItem extends GameObject implements Prize{

    private boolean revealed = false;
    private int point;

    public BoostItem(double x, double y, BufferedImage style) {
        super(x, y, style);
        setDimension(48, 48);
    }

    public abstract void onTouch(Mario mario, GameEngine engine);

    @Override
    public int getPoint() {
        return point;
    }

    @Override
    public void updateLocation(){
        if(revealed){
            super.updateLocation();
        }
    }

    @Override
    public void draw(Graphics g){
        if(revealed){
            g.drawImage(getStyle(), (int)getX(), (int)getY(), null);
        }
    }

    @Override
    public void reveal(){
        setY(getY()-48);
        revealed = true;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
