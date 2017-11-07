package model;

import manager.ButtonAction;
import model.brick.Brick;
import model.brick.BrickType;
import model.brick.NonEmptyBrick;
import model.enemy.Enemy;
import model.hero.Mario;
import model.hero.MarioForm;
import model.prize.BoostItem;
import model.prize.BoostType;
import model.prize.Coin;
import model.prize.Prize;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class Map {

    private double timeLimit;

    private int remainingLives;

    private Mario mario;

    private int acquiredCoins;

    private int acquiredPoints;

    private ArrayList<Brick> bricks = new ArrayList<>();

    private ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Point[]> ground = new ArrayList<>();

    private ArrayList<Fireball> fireballs = new ArrayList<>();

    private ArrayList<Coin> coins = new ArrayList<>();

    private ArrayList<BoostItem> boostItems = new ArrayList<>();

    private BufferedImage backgroundImage;


    public Map(int remainingLives, double timeLimit){
        try{
            backgroundImage = ImageIO.read(new File("./src/media/background/background.png"));
            System.out.println("Background image has been loaded..");
        }
        catch(IOException e){
            backgroundImage = null;
            System.out.println("Background image does not exist!");
        }

        acquiredPoints = 0;
        acquiredCoins = 0;
        this.remainingLives = remainingLives;
        this.timeLimit = timeLimit;

        mario = new Mario();

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

    public ArrayList<Point> getBoostItemLocations() {
        return getLocations(boostItems);
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

    private void addMarioForm(MarioForm formToAdd){
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

    public Brick getBrickWithIndex(int i) {
        return bricks.get(i);
    }

    public void removeFireballWithIndex(int i) {
        fireballs.remove(i);
    }

    public double getTimeLimit() {
        return timeLimit;
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

    public BoostItem getBoostItemWithIndex(int i) {
        return boostItems.get(i);
    }

    public void acquireBoostItem(BoostItem boost) {
        BoostType boostType = boost.getType();

        switch (boostType){
            case FIRE_FLOWER:{
                addMarioForm(MarioForm.FIRE);
            }
            case ONEUP_MUSH:{
                remainingLives++;
            }
            case SUPER_MUSH:{
                addMarioForm(MarioForm.SUPER);
            }
            case SUPER_STAR:{
                addMarioForm(MarioForm.INVINCIBLE);
            }
        }

    }

    public boolean isDead() {
        return remainingLives == 0;
    }

    public void touchedEnemy(Enemy enemy) {
        Set<MarioForm> marioForms = mario.getForms();
        if(marioForms.contains(MarioForm.INVINCIBLE)){
            killEnemy(enemy);
        }
        else if(marioForms.contains(MarioForm.FIRE)){
            killEnemy(enemy);
            marioForms.remove(MarioForm.FIRE);
            mario.setForms(marioForms);
        }
        else if(marioForms.contains(MarioForm.SUPER)){
            marioForms.remove(MarioForm.SUPER);
            mario.setForms(marioForms);
        }
        else{
            remainingLives--;
        }
    }

    public void revealBrick(Brick brickToReveal) {
        Set<MarioForm> marioForms = mario.getForms();

        if(brickToReveal instanceof NonEmptyBrick){
            Prize revealedPrize = ((NonEmptyBrick) brickToReveal).revealPrize();
            if(revealedPrize instanceof Coin){
                coins.add((Coin) revealedPrize);
            }
            else if(revealedPrize instanceof BoostItem){
                boostItems.add((BoostItem) revealedPrize);
            }
        }

        if(marioForms.contains(MarioForm.SUPER)){
            if(brickToReveal.getType() == BrickType.ORD_BREAKABLE){
                bricks.remove(brickToReveal);
            }
        }
    }

    public void addBrick(Brick brick) {
        bricks.add(brick);
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public void addGroundPointPair(Point[] groundPointPair) {
        ground.add(groundPointPair);
    }
}
