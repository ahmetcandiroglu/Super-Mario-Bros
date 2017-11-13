package model.hero;

import manager.Camera;
import view.Animation;
import model.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Mario extends GameObject{

    private int remainingLives;
    private int coins;
    private int points;
    private double invincibilityTimer;
    private boolean strong, shootFire, invincible;
    private Animation marioAnimation;
    private boolean toRight = true;

    public Mario(double x, double y, BufferedImage[] leftFrames, BufferedImage[] rightFrames){
        super(x, y, null);
        setDimension(48,96);

        remainingLives = 3;
        points = 0;
        coins = 0;
        invincibilityTimer = 0;

        marioAnimation = new Animation(leftFrames, rightFrames);
        setStyle(marioAnimation.getRightFrames()[1]);
    }

    @Override
    public void draw(Graphics g){
        if((isJumping() || isFalling()) && toRight){
            setStyle(marioAnimation.getRightFrames()[0]);
        }
        else if(isJumping() || isFalling()){
            setStyle(marioAnimation.getLeftFrames()[0]);
        }
        else if(getVelX() != 0){
            setStyle(marioAnimation.animate(5, toRight));
        }
        else {
            if(toRight){
                setStyle(marioAnimation.getRightFrames()[1]);
            }
            else
                setStyle(marioAnimation.getLeftFrames()[1]);
        }
        super.draw(g);
    }

    public int getRemainingLives() {
        return remainingLives;
    }

    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }

    public double getInvincibilityTimer() {
        return invincibilityTimer;
    }

    public void setInvincibilityTimer(double invincibilityTimer) {
        this.invincibilityTimer = invincibilityTimer;
    }

    public boolean isStrong() {
        return strong;
    }

    public void setStrong(boolean strong) {
        this.strong = strong;
    }

    public boolean isShootFire() {
        return shootFire;
    }

    public void setShootFire(boolean shootFire) {
        this.shootFire = shootFire;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public void acquirePoints(int point){
        points = points + point;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void jump() {
        if(!isJumping() && !isFalling()){
            setJumping(true);
            setVelY(10);
        }
    }

    public void move(boolean toRight, Camera camera) {
        System.out.println(camera.getX() + " - " + getX());

        if(toRight){
            setVelX(5);
        }
        else if(camera.getX() < getX()){
            setVelX(-5);
        }

        this.toRight = toRight;
    }
}
