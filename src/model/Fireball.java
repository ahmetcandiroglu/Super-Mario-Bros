package model;


import java.awt.*;

public class Fireball extends GameObject{

    public static final Dimension DIMENSION = new Dimension(5, 5);

    private boolean hitEnemy;

    public void move(){}

    public boolean isHitEnemy() {
        return hitEnemy;
    }

    public void setHitEnemy(boolean hitEnemy) {
        this.hitEnemy = hitEnemy;
    }

    @Override
    public void draw() {

    }
}
