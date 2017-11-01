package model;

import model.brick.Brick;
import model.brick.BrickType;
import model.hero.Hero;
import model.prize.Prize;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Map {

    private Hero hero;

    private ArrayList<Brick> bricks = new ArrayList<>();

    private ArrayList<Point> ground = new ArrayList<>();

    private ArrayList<Fireball> fireballs = new ArrayList<>();

    private ImageIcon background;

    private Point start, finish;

    public Hero getHero() {
        return hero;
    }

    public Point getHeroLocation(){
        return null;
    }

    public Prize revealBrick(Brick brick){
        return null;
    }

    public ArrayList<Point> getEnemyLocations(){
        return null;
    }

    public ArrayList<Point> getBrickLocations(){
        return null;
    }

    public Point getBrickLocation(Brick brick){
        return null;
    }

    public void changeBrickType(Brick brick, BrickType type){

    }
}
