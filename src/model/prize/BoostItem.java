package model.prize;

import model.GameObject;
import model.hero.Mario;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BoostItem extends GameObject implements Prize{

    private boolean revealed = false;

    private int point;

    public BoostItem(double x, double y, BufferedImage style) {

        super(x, y, style);
    }

    public abstract Mario onTouch(Mario mario);

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

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

    public void setPoint(int point) {
        this.point = point;
    }
}
