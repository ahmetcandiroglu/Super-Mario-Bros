package model.prize;

import manager.GameEngine;
import model.GameObject;
import model.hero.Mario;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Coin extends GameObject implements Prize{

    private int point;
    private boolean revealed, acquired = false;
    private int revealBoundary;

    public Coin(double x, double y, BufferedImage style, int point){
        super(x, y, style);
        this.point = point;
        revealed = false;
        setDimension(30, 42);
        revealBoundary = (int)getY() - getDimension().height;
    }

    @Override
    public int getPoint() {
        return point;
    }

    @Override
    public void reveal() {
        revealed = true;
    }

    @Override
    public void onTouch(Mario mario, GameEngine engine) {
        if(!acquired){
            acquired = true;
            mario.acquirePoints(point);
            engine.playCoin();
        }
    }

    @Override
    public void updateLocation(){
        if(revealed){
            setY(getY() - 5);
        }
    }

    @Override
    public void draw(Graphics g){
        if(revealed){
            g.drawImage(getStyle(), (int)getX(), (int)getY(), null);
        }
    }

    public int getRevealBoundary() {
        return revealBoundary;
    }
}
