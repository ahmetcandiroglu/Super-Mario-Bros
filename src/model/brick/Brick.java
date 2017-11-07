package model.brick;

import model.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Brick extends GameObject{

    public static final Dimension DIMENSION = new Dimension(16, 16);

    private BrickType type;

    public Brick(Point location, BufferedImage style){
        setLocation(location);
        setStyle(style);
    }

    public BrickType getType() {
        return type;
    }

    public void setType(BrickType type) {
        this.type = type;
    }


    @Override
    public void draw() {

    }
}
