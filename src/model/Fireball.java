package model;


import java.awt.image.BufferedImage;

public class Fireball extends GameObject{

    private boolean hitEnemy;

    public Fireball(double x, double y, BufferedImage style) {
        super(x, y, style);
        setVelX(5);
        setDimension(16, 16);
    }

    public boolean isHitEnemy() {
        return hitEnemy;
    }

    public void setHitEnemy(boolean hitEnemy) {
        this.hitEnemy = hitEnemy;
    }


}
