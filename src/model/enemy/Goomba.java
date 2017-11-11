package model.enemy;

import java.awt.image.BufferedImage;

public class Goomba extends Enemy{

    public Goomba(double x, double y, BufferedImage style) {
        super(x, y, style);
        setVelX(5);
        setVelY(0);
    }

}
