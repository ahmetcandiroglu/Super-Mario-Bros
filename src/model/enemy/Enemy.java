package model.enemy;

import model.GameObject;

import java.awt.image.BufferedImage;


public abstract class Enemy extends GameObject{

    public Enemy(double x, double y, BufferedImage style) {
        super(x, y, style);
        setDimension(48, 48);
    }
}
