package model.hero;

import model.GameObject;

import java.awt.image.BufferedImage;

public abstract class Mario extends GameObject{

    private int remainingLives;

    private int coins;

    private int points;

    private double invincibilityTimer;

    private boolean strong, shootFire, invincible;

    public Mario(double x, double y, BufferedImage style){
        super(x, y, style);
        remainingLives = 3;
        points = 0;
        coins = 0;
        invincibilityTimer = 0;
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
}
