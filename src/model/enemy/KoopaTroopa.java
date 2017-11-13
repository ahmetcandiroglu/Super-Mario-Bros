package model.enemy;

import java.awt.*;
import java.awt.image.BufferedImage;

public class KoopaTroopa extends Enemy{

    public KoopaTroopa(double x, double y, BufferedImage style) {
        super(x, y, style);
        setVelX(8);
        setDimension(48, 64);
    }

    @Override
    public void draw(Graphics g){
        g.drawImage(getStyle(), (int)getX(), (int)getY()-48, null);
    }

}
