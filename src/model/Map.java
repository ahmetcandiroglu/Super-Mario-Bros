package model;

import manager.ButtonAction;
import model.brick.Brick;
import model.brick.BrickType;
import model.enemy.Enemy;
import model.hero.Mario;
import model.hero.MarioForm;
import model.prize.BoostItem;
import model.prize.Coin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Map {

    private double timeLimitInMicro;
    private Mario mario;
    private int acquiredCoins;
    private int acquiredPoints;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Point> ground = new ArrayList<>();
    private ArrayList<Fireball> fireballs = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();
    private ArrayList<BoostItem> boostItems = new ArrayList<>();
    private BufferedImage backgroundImage;
    private Point start, finish;


    public Map(){
        try{
            backgroundImage = ImageIO.read(new File("./src/media/temp_background.png"));
            System.out.println("Background image has been loaded..");
        }
        catch(IOException e){
            backgroundImage = null;
            System.out.println("Background image does not exist!");
        }

    }

    public Mario getMario() {
        return mario;
    }

    public Point getMarioLocation(){
        return mario.getLocation();
    }

    public void actOnMario(ButtonAction action){
    	Point currentLocation = getMarioLocation();
    	Point newLocation;
    	
    	
        switch (action){
            case JUMP:
            {
            	for( int i = 0; i <= 30; i++)
            	{
            		int x = getMarioLocation().x;
                	int y = getMarioLocation().y;                	
            		newLocation = new Point( x, y - 1); 
            		mario.setLocation(newLocation);
            	}
            	for( int i = 0; i <= 30; i++)
            	{
            		int x = getMarioLocation().x;
                	int y = getMarioLocation().y;                	
            		newLocation = new Point( x, y + 1); 
            		mario.setLocation(newLocation);
            	}
            }            
            case M_RIGHT:
            {
            	newLocation = new Point(currentLocation.x + 1, currentLocation.y);
            	mario.setLocation(newLocation);
            }
            case M_LEFT:
            {
            	newLocation = new Point(currentLocation.x - 1, currentLocation.y);
            	mario.setLocation(newLocation);
            }
            case FIRE:
            {
            	
            }
        }
    }

    public ArrayList<Point> getEnemyLocations(){
        return getLocations(enemies);
    }

    public ArrayList<Point> getBrickLocations(){
        return getLocations(bricks);
    }

    public ArrayList<Point> getCoinLocations() {
        return getLocations(coins);
    }

    public ArrayList<Point> getFireballLocations() {
        return getLocations(fireballs);
    }

    private ArrayList<Point> getLocations(ArrayList<? extends GameObject> list){
        ArrayList<Point> locations = new ArrayList<>();

        for(GameObject object : list){
            locations.add(object.getLocation());
        }

        return locations;
    }

    public void changeBrickType(Brick brick, BrickType type){
        int index = bricks.indexOf(brick);
        if(index > -1){
            brick.setType(type);
            bricks.set(index, brick);
        }
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public void killEnemy(Enemy enemyToKill) {
        enemies.remove(enemyToKill);
    }

    public void addMarioForm(MarioForm formToAdd){
        mario.addForm(formToAdd);
    }

    public void removeMarioForm(MarioForm formToRemove){
        mario.removeForm(formToRemove);
    }

    public Dimension getMarioDimensions() {
        return mario.getDimension();
    }

    public ArrayList<Dimension> getEnemyDimensions() {
        return getDimensions(enemies);
    }

    private ArrayList<Dimension> getDimensions(ArrayList<? extends GameObject> list){
        ArrayList<Dimension> dimensions = new ArrayList<>();

        for(GameObject object : list){
            dimensions.add(object.getDimension());
        }

        return dimensions;
    }

    public Enemy getEnemyWithIndex(int i) {
        return enemies.get(i);
    }

    public Object getBrickWithIndex(int i) {
        return bricks.get(i);
    }

    public void removeFireballWithIndex(int i) {
        fireballs.remove(i);
    }

    public double getTimeLimitInMicro() {
        return timeLimitInMicro;
    }

    public void acquirePoints(int point) {
        acquiredPoints =+ point;
    }

    public void acquireCoin(Coin coin) {
        acquiredCoins++;
        acquiredPoints =+ coin.getPoint();
        removeCoin(coin);
    }

    private void removeCoin(Coin coin) {
        coins.remove(coin);
    }



    public Coin getCoinWithIndex(int i) {
        return coins.get(i);
    }
}
