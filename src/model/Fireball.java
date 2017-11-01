package model;


public class Fireball extends GameObject{

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
