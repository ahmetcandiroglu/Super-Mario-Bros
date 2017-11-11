package model;

import model.brick.Brick;
import model.enemy.Enemy;
import model.hero.Mario;
import model.prize.BoostItem;
import model.prize.Coin;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {

    private double timeLimit;

    private Mario mario;

    private ArrayList<Brick> bricks = new ArrayList<>();

    private ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Brick> groundBricks = new ArrayList<>();

    private ArrayList<Fireball> fireballs = new ArrayList<>();

    private ArrayList<Coin> coins = new ArrayList<>();

    private ArrayList<BoostItem> boostItems = new ArrayList<>();

    private BufferedImage backgroundImage;


    public Map(double timeLimit, BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
        this.timeLimit = timeLimit;
    }

    public double getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(double timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Mario getMario() {
        return mario;
    }

    public void setMario(Mario mario) {
        this.mario = mario;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Brick> getGroundBricks() {
        return groundBricks;
    }

    public void setGroundBricks(ArrayList<Brick> groundBricks) {
        this.groundBricks = groundBricks;
    }

    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }

    public void setFireballs(ArrayList<Fireball> fireballs) {
        this.fireballs = fireballs;
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public void setCoins(ArrayList<Coin> coins) {
        this.coins = coins;
    }

    public ArrayList<BoostItem> getBoostItems() {
        return boostItems;
    }

    public void setBoostItems(ArrayList<BoostItem> boostItems) {
        this.boostItems = boostItems;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void addBrick(Brick brick) {
        this.bricks.add(brick);
    }

    public void addGroundBrick(Brick brick) {
        this.groundBricks.add(brick);
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }
}
