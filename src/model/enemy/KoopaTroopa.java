package model.enemy;

import java.awt.image.BufferedImage;

public class KoopaTroopa extends Enemy{

    public KoopaTroopa(double x, double y, BufferedImage style) {
        super(x, y, style);
        setVelX(3);
    }
}
